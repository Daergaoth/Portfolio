package com.daergaoth.videoplayer.service;

import com.daergaoth.videoplayer.constants.ApplicationConstants;
import com.daergaoth.videoplayer.domain.*;
import com.daergaoth.videoplayer.helper.AlphanumComparator;
import com.daergaoth.videoplayer.repositories.FolderRepository;
import com.daergaoth.videoplayer.repositories.PlaylistRepository;
import com.daergaoth.videoplayer.repositories.SubtitleRepository;
import com.daergaoth.videoplayer.repositories.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class FileSystemReaderService {

	@Autowired
	private FolderRepository folderRepository;

	@Autowired
	private FolderService folderService;

	@Autowired
	private PlaylistRepository playRepository;

	private int videoAdded = 0;

	private int subtitleAdded = 0;

	private int folderAdded = 0;

	private int videoRemoved = 0;

	private int subtitleRemoved = 0;

	private int folderRemoved = 0;

	private int playlistDeleted = 0;

	private int playlistCreated = 0;

	private int videoConnectedWithSubtitle = 0;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private SubtitleRepository subtitleRepository;

	public FileSystemReaderService(PlaylistRepository playRepository, FolderRepository folderRepository,
			VideoRepository videoRepository, SubtitleRepository subtitleRepository) {
		super();
		this.folderRepository = folderRepository;
		this.videoRepository = videoRepository;
		this.subtitleRepository = subtitleRepository;
		this.playRepository = playRepository;
	}

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void startUpResolve() {
		resolveFiles();
		folderService.createNarutoFillerFreePlaylist();
	}

	private void connectVideoWithSubtitle() {
		List<Video> allVideo = videoRepository.findAll();
		List<Subtitle> allSubtitle = subtitleRepository.findAll();
		for (Video video : allVideo) {
			for (Subtitle subtitle : allSubtitle) {

				if (video.getTitle().contains(subtitle.getTitle())) {
					if (Objects.isNull(video.getSubtitle()) || Objects.isNull(subtitle.getVideo())) {
						video.setSubtitle(subtitle);
						subtitle.setVideo(video);
						this.videoConnectedWithSubtitle++;
					}
				} else if (subtitle.getTitle().contains(video.getTitle())) {
					if (Objects.isNull(video.getSubtitle()) || Objects.isNull(subtitle.getVideo())) {
						video.setSubtitle(subtitle);
						subtitle.setVideo(video);
						this.videoConnectedWithSubtitle++;
					}
				}

			}
		}
	}

	private void removeNotValidFilesAndFolders() {
		List<Video> allVideo = videoRepository.findAll();
		for (Video video : allVideo) {
			File file = new File(video.getAbsolutePath());
			if (!file.exists()) {
				this.videoRemoved++;
				videoRepository.delete(video);
			}
		}
		List<Subtitle> allSubtitle = subtitleRepository.findAll();
		for (Subtitle subtitle : allSubtitle) {
			File file = new File(subtitle.getPath());
			if (!file.exists()) {
				this.subtitleRemoved++;
				subtitleRepository.delete(subtitle);
			}
		}
		List<Folder> allFolder = folderRepository.findAll();
		for (Folder folder : allFolder) {
			File file = new File(folder.getPath());
			if (!file.exists()) {
				this.folderRemoved++;
				folderRepository.delete(folder);
			}
		}
	}

	public boolean resolveFiles() {
		this.videoAdded = 0;
		this.subtitleAdded = 0;
		this.folderAdded = 0;
		this.folderRemoved = 0;
		this.subtitleRemoved = 0;
		this.videoRemoved = 0;
		this.videoConnectedWithSubtitle = 0;
		long start = System.currentTimeMillis();
		logger.debug("Databse update started.");
		try {
			removeNotValidFilesAndFolders();
			File root = new File(ApplicationConstants.VIDEO_ROOT_FOLDER);
			resolveCurrentFile(root);
			connectVideoWithSubtitle();
			createDefaultPlaylists();
			long end = System.currentTimeMillis();
			long minutes = (end - start) / 60000;
			long seconds = ((end - start) / 1000) % 60;
			logger.debug("Finished update after " + minutes + " minutes and " + seconds + " seconds");
			logger.debug("\nAdded folder: " + this.folderAdded + "\nAdded video: " + this.videoAdded
					+ "\nAdded subtitle: " + this.subtitleAdded + "\nAdded playlist: " + this.playlistCreated
					+ "\nRemoved folder: " + this.folderRemoved + "\nRemoved video: " + this.videoRemoved
					+ "\nRemoved subtitle: " + this.subtitleRemoved + "\nRemoved playlist: " + this.playlistDeleted
					+ "\nAnd connected " + this.videoConnectedWithSubtitle + " video with its subtitle.");
			return true;
		} catch (Exception e) {
			logger.error("Failed update.");
			e.printStackTrace();
			return false;
		}
	}

	private void createDefaultPlaylists() {
		checkExistingPlaylists();
		List<Playlist> playlists = playRepository.findAll();
		List<Video> videoList = videoRepository.findAll();
		File rootFolder = new File(ApplicationConstants.VIDEO_ROOT_FOLDER);
		File[] rootFolderChildren = rootFolder.listFiles();
		Playlist existing = null;
		for (File child : rootFolderChildren) {
			if (child.isDirectory()) {
				Playlist playlistToCreate = new Playlist();
				playlistToCreate.setName(child.getName() + " - default");
				int counter = 0;
				for (Video video : videoList) {
					if (video.getAbsolutePath().contains(child.getName())) {
						playlistToCreate.getListOfAllVideo().add(video);
						if (counter == 0) {
							playlistToCreate.setLastViewedVideo(video);
						}
						counter++;
					}
				}
				boolean shouldISavePlaylist = true;
				for (Playlist playlist : playlists) {
					if (playlist.getName().equals(playlistToCreate.getName())) {
						shouldISavePlaylist = false;
						existing = playlist;
					}
				}
				if (shouldISavePlaylist) {
					playRepository.save(playlistToCreate);
					this.playlistCreated++;
				} else {
					existing.setListOfAllVideo(playlistToCreate.getListOfAllVideo());
					existing.setListOfFillerElements(playlistToCreate.getListOfFillerElements());
					existing.setListOfFillerVideo(playlistToCreate.getListOfFillerVideo());
					if (existing.getListOfAllVideo().size() > 0
							&& !existing.getListOfAllVideo().contains(existing.getLastViewedVideo())) {
						existing.setLastViewedVideo(existing.getListOfAllVideo().get(0));
					}
				}
			}
		}
	}

	private void checkExistingPlaylists() {
		List<Playlist> playlists = playRepository.findAll();
		for (Playlist playlist : playlists) {
			boolean shouldIDeleteThisPlaylist = false;
			for (Video video : playlist.getListOfAllVideo()) {
				File videoFile = new File(video.getAbsolutePath());
				if (!videoFile.exists()) {
					shouldIDeleteThisPlaylist = true;
				}
			}
			if (shouldIDeleteThisPlaylist) {
				playRepository.delete(playlist);
				this.playlistDeleted++;
			}
		}
	}

	private DomainInterfaces resolveCurrentFile(File file) {
		if (!file.getName().toLowerCase().contains("sample")) {
			if (file.isDirectory()) {
				List<Folder> folderByPath = folderRepository.getFolderByPath(file.getAbsolutePath());
				Folder currentFolder;
				if (folderByPath.size() > 0) {
					currentFolder = folderByPath.get(0);
				} else {
					this.folderAdded++;
					currentFolder = new Folder();
					currentFolder.setName(file.getName());
					currentFolder.setPath(file.getAbsolutePath());
					currentFolder = folderRepository.save(currentFolder);
				}

				File[] fileListOriginal = file.listFiles();
				File[] fileList = sortFileArray(fileListOriginal);
				/* File[] fileList = file.listFiles(); */
				for (File currentFile : fileList) {
					DomainInterfaces tempResult = resolveCurrentFile(currentFile);
					if (!Objects.isNull(tempResult)) {
						if (tempResult instanceof Video) {
							Video result = (Video) tempResult;
							boolean isVideoAlreadyPresent = false;
							for (Video folderVideo : currentFolder.getVideos()) {
								if (folderVideo.getAbsolutePath().equals(result.getAbsolutePath())) {
									isVideoAlreadyPresent = true;
								}
							}
							if (!isVideoAlreadyPresent) {
								currentFolder.getVideos().add(result);
								result.setFolder(currentFolder);
							}
						} else if (tempResult instanceof Subtitle) {
							Subtitle result = (Subtitle) tempResult;
							boolean isSubtitleAlreadyPresent = false;
							for (Subtitle folderVideo : currentFolder.getSubtitles()) {
								if (folderVideo.getPath().equals(result.getPath())) {
									isSubtitleAlreadyPresent = true;
								}
							}
							if (!isSubtitleAlreadyPresent) {
								currentFolder.getSubtitles().add(result);
								result.setFolder(currentFolder);
							}
						}
					}
				}
				return null;
			} else {
				try {
					long sizeInBytes = Files.size(Paths.get(file.getAbsolutePath()));
					/* if (sizeInBytes <= 1073741824) { */
					if (file.getName().contains(".srt")) {
						List<Subtitle> subtitleList = subtitleRepository.getFolderByPath(file.getAbsolutePath());
						if (subtitleList.size() > 0) {
							return subtitleList.get(0);
						} else {
							this.subtitleAdded++;
							Subtitle newSubtitle = new Subtitle();
							newSubtitle.setExtension(".srt");
							newSubtitle.setFullName(file.getName());
							newSubtitle.setPath(file.getAbsolutePath());
							newSubtitle.setTitle(file.getName().replace(".srt", ""));
							return subtitleRepository.save(newSubtitle);
						}
					} else if ((file.getName().contains(".mp4")
							|| file.getName().contains(".mkv")) && !file.getName().toLowerCase().contains("sample")) {
						List<Video> videoList = videoRepository.getFolderByPath(file.getAbsolutePath());
						if (videoList.size() > 0) {
							return videoList.get(0);
						} else {
							this.videoAdded++;
							Video newVideo = new Video();
							String name = file.getName();
							newVideo.setAbsolutePath(file.getAbsolutePath());
							newVideo.setExtension(name.substring(name.length() - 4, name.length()));
							newVideo.setFullName(name);
							newVideo.setTitle(name.substring(0, name.length() - 4));
							return videoRepository.save(newVideo);
						}
					} else {
						return null;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		}else {
			return null;
		}
	}

	private File[] sortFileArray(File[] fileListOriginal) {
		String[] fileNameArray = new String[fileListOriginal.length];
		for (int i = 0; i < fileNameArray.length; i++) {
			fileNameArray[i] = fileListOriginal[i].getName();
		}
		/* fileNameArray = Stream.of(fileNameArray).sorted().toArray(String[]::new); */
		fileNameArray = Arrays.asList(fileNameArray).stream().sorted(new AlphanumComparator()).toArray(String[]::new);
		File[] resultArray = new File[fileListOriginal.length];
		for (int i = 0; i < fileNameArray.length; i++) {
			for (File file : fileListOriginal) {
				if (file.getName().equals(fileNameArray[i])) {
					resultArray[i] = file;
				}
			}
		}
		return resultArray;
	}
}