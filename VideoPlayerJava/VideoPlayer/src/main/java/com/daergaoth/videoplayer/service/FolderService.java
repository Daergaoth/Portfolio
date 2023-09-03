package com.daergaoth.videoplayer.service;

import com.daergaoth.videoplayer.domain.FillerRecord;
import com.daergaoth.videoplayer.domain.Folder;
import com.daergaoth.videoplayer.domain.Playlist;
import com.daergaoth.videoplayer.domain.Video;
import com.daergaoth.videoplayer.dto.FillerElementDTO;
import com.daergaoth.videoplayer.dto.FolderDTO;
import com.daergaoth.videoplayer.dto.PlaylistDTO;
import com.daergaoth.videoplayer.repositories.FolderRepository;
import com.daergaoth.videoplayer.repositories.PlaylistRepository;
import com.daergaoth.videoplayer.repositories.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FolderService {

	private final FolderRepository folderRepository;

	private final PlaylistRepository playlistRepository;

	private final VideoRepository videolistRepository;

	@Autowired
	public FolderService(FolderRepository folderRepository, PlaylistRepository playlistRepository,
			VideoRepository videolistRepository) {
		super();
		this.folderRepository = folderRepository;
		this.playlistRepository = playlistRepository;
		this.videolistRepository = videolistRepository;
	}

	public List<FolderDTO> getAllFolderWithContent() {
		List<Folder> allFolder = folderRepository.findAll();
		List<FolderDTO> result = new ArrayList<>();
		for (Folder folder : allFolder) {
			if (folder.getVideos().size() > 0) {
				result.add(new FolderDTO(folder));
			}
		}
		return result;
	}

	public FolderDTO getFolderById(Long id) {
		Optional<Folder> folderInDB = folderRepository.findById(id);
		return folderInDB.map(FolderDTO::new).orElse(null);
	}

	public Playlist createPlaylist(PlaylistDTO playlistDTO) {
		Playlist newPlaylist = new Playlist();
		if(playlistDTO.getListOfAllVideo().size() > 0) {
			newPlaylist.setName(playlistDTO.getName());
			int counter = 0;
			for (Long videoID : playlistDTO.getListOfAllVideo()) {
				Optional<Video> optionalVideo = videolistRepository.findById(videoID);
				if (optionalVideo.isPresent()) {
					Video video = optionalVideo.get();
					if(counter == 0) {
						newPlaylist.setLastViewedVideo(video);
						counter++;
					}
					newPlaylist.getListOfAllVideo().add(video);
					if (playlistDTO.getListOfFillerVideo().contains(videoID)) {
						newPlaylist.getListOfFillerVideo().add(video);
					}
				}
			}
			newPlaylist.setLastVideoViewedUntil(0);
			
			for (FillerElementDTO fillerElement : playlistDTO.getListOfFillerElements()) {
				Optional<Video> optionalVideo = videolistRepository.findById(fillerElement.getVideoID());
				if (optionalVideo.isPresent()) {
					FillerRecord fillerRecord = new FillerRecord();
					fillerRecord.setPlaylist(newPlaylist);
					fillerRecord.setStart(fillerElement.getStart());
					fillerRecord.setEnd(fillerElement.getEnd());
					fillerRecord.setVideo(optionalVideo.get());
					newPlaylist.getListOfFillerElements().add(fillerRecord);
				}
			}
			playlistRepository.save(newPlaylist);
		}
		return newPlaylist;
	}

	public List<PlaylistDTO> getAllPlaylist() {
		List<Playlist> dbList = playlistRepository.findAll();
		List<PlaylistDTO> result = new ArrayList<>();
		for(Playlist playlist : dbList) {
			if(playlist.getListOfAllVideo().size() > 0) {
				//Collections.sort(playlist.getListOfAllVideo());
				result.add(new PlaylistDTO(playlist));
			}
		}
		return result;
	}

	public Playlist resetPlaylist(Long id) {
		Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
		if(optionalPlaylist.isPresent()) {
			Playlist playlist = optionalPlaylist.get();
			if(playlist.getListOfAllVideo().size() > 0) {
				playlist.setLastViewedVideo(playlist.getListOfAllVideo().get(0));
				playlist.setLastVideoViewedUntil(0);
				playlistRepository.save(playlist);
				for (Video video : playlist.getListOfAllVideo()) {
					video.setWatched(0);
					videolistRepository.save(video);
				}
				return playlist;
			}else {
				playlistRepository.delete(playlist);
				return null;
			}
		}else {
			return null;
		}
	}

	public Playlist updateLastWatchedVideoInPlaylist(Long id, Long videoID) {
		Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);
		Optional<Video> optionalVideo = videolistRepository.findById(videoID);
		
		if(optionalPlaylist.isPresent() && optionalVideo.isPresent()) {
			Playlist playlist = optionalPlaylist.get();
			playlist.setLastViewedVideo(optionalVideo.get());
			playlistRepository.save(playlist);
			return playlist;
		}else {
			return null;
		}
	}

	public Playlist createNarutoFillerFreePlaylist() {
		List<Playlist> narutoDefaultList = playlistRepository.getPlaylistByName("Naruto Shippuuden (TV_480) - default");
		List<Playlist> narutoFillerFreeList = playlistRepository.getPlaylistByName("Naruto - Filler Free");
		if(narutoDefaultList.size() == 1 && narutoFillerFreeList.size() == 0){
			Playlist narutoDefault = narutoDefaultList.get(0);
			PlaylistDTO playlistDTO = new PlaylistDTO(narutoDefault);
			playlistDTO.setId(null);
			playlistDTO.setName("Naruto - Filler Free");
			//Example ids of an old database records
			int[] fillerIdArray = {27,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,143,144,145,146,147,148,149,150,169,170,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,256,257,258,259,270,278,279,280,283,284,285,286,287,288,289,290,291,292,293,294,302,303,304,305,306,307,308,309,310,311,312,313,314,315,316,317,318,319,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360,375,376,387,388,389,393,394,395,396,397,398,399,400,401,402,403,404,405,406,407,408,409,410,411,412,415,416,421,422,426,427,428,429,430,431,432,433,434,435,436,437,438,439,440,441,442,443,444,445,446,447,448,449,463,464,465,466,467,468,479,480,481,482};
			for (int i = 0; i < fillerIdArray.length; i++) {
				playlistDTO.getListOfFillerVideo().add(playlistDTO.getListOfAllVideo().get((fillerIdArray[i] - 1)));

			}
			return createPlaylist(playlistDTO);
		}

		return null;
	}
}