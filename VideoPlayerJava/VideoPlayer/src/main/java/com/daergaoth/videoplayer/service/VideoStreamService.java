package com.daergaoth.videoplayer.service;

import com.daergaoth.videoplayer.domain.Video;
import com.daergaoth.videoplayer.dto.Subtitle;
import com.daergaoth.videoplayer.helper.SubtitleElement;
import com.daergaoth.videoplayer.repositories.VideoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class VideoStreamService {

	@Autowired
	VideoRepository videoRepository;

	@Autowired
	private ResourceLoader resourceLoader;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public VideoStreamService(VideoRepository videoRepository) {
		super();
		this.videoRepository = videoRepository;
	}

	public Mono<Resource> prepareContent(Long id) {
		Optional<Video> optional = videoRepository.findById(id);
		return optional.map(video -> Mono.fromSupplier(() -> resourceLoader.getResource("file:" + video.getAbsolutePath()))).orElse(null);
	}

	public Subtitle getSubtitle(Long videoID) {

		Optional<Video> video = videoRepository.findById(videoID);
		if (video.isPresent() && !Objects.isNull(video.get().getSubtitle())) {
			File subtitle = new File(video.get().getSubtitle().getPath());
			Subtitle subtitleResult = new Subtitle();
			subtitleResult.setTitle(video.get().getTitle());

			try (
					FileInputStream is = new FileInputStream(subtitle);
					InputStreamReader isr = new InputStreamReader(is, "Cp1250");
					BufferedReader br = new BufferedReader(isr)
			) {
				String line;
				SubtitleElement element = new SubtitleElement();
				boolean isCurrentLineTime = false;
				while ((line = br.readLine()) != null) {
					if (line.contains(" --> ")) {
						element = new SubtitleElement();
						String[] lineParts = line.split(" --> ");
						element.setStart(getSeconds(lineParts[0]));
						element.setEnd(getSeconds(lineParts[1]));
						isCurrentLineTime = true;
					} else {
						if (isCurrentLineTime) {
							String utf8EncodedString = new String(line.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
							String content = utf8EncodedString

									.replaceAll("Ăł", "ó").replaceAll("ĂĽ", "ü").replaceAll("Ă»", "ű")
									.replaceAll("Ĺ±", "ű").replaceAll("Ă­", "í").replaceAll("Ăˇ", "á")
									.replaceAll("Ăş", "ú").replaceAll("Ă©", "é").replaceAll("Ăµ", "ő")
									.replaceAll("Ĺ‘", "ő").replaceAll("Ă¶", "ö").replaceAll("Ă•", "Ő")
									.replaceAll("Ă‰", "É").replaceAll("Ă“", "Á").replaceAll("Ăś", "Ü")
									.replaceAll("Ăš", "Ú").replaceAll("ĂŤ", "Í").replaceAll("Ă–", "Ö")
									.replaceAll("â€ž", "\"").replaceAll("â€ť", "\"").replaceAll("<i>", "")
									.replaceAll("</i>", "");



							if (element.getContent().equals("")) {
								element.setContent(content);
							} else {
								element.setContent(element.getContent() + "\n" + content);
							}

						}
						if (line.equals("")) {
							subtitleResult.getElements().add(element);
							isCurrentLineTime = false;
						}

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return subtitleResult;
		} else return video.map(value -> new Subtitle(value.getTitle())).orElse(null);

	}

	public Subtitle getSubtitleForPhone(Long videoID) {
		Optional<Video> video = videoRepository.findById(videoID);
		if (video.isPresent() && !Objects.isNull(video.get().getSubtitle())) {
			File subtitle = new File(video.get().getSubtitle().getPath());
			Subtitle subtitleResult = new Subtitle();
			subtitleResult.setTitle(video.get().getTitle());
			try (BufferedReader br = new BufferedReader(new FileReader(subtitle))) {
				String line;
				SubtitleElement element = new SubtitleElement();
				boolean isCurrentLineTime = false;

				while ((line = br.readLine()) != null) {

					if (line.contains(" --> ")) {
						element = new SubtitleElement();
						String[] lineParts = line.split(" --> ");
						element.setStart(getSeconds(lineParts[0]));
						element.setEnd(getSeconds(lineParts[1]));
						isCurrentLineTime = true;
					} else {
						if (isCurrentLineTime) {
							String content = line.replaceAll("í", "i");
							content = line.replaceAll("ö", "o");
							content = line.replaceAll("ő", "o");
							content = line.replaceAll("ó", "o");
							content = line.replaceAll("ü", "u");
							content = line.replaceAll("ú", "u");
							content = line.replaceAll("ű", "u");
							content = line.replaceAll("é", "e");
							content = line.replaceAll("á", "a");

							if (element.getContent().equals("")) {
								element.setContent(line);
							} else {
								element.setContent(element.getContent() + "\n" + line);
							}

						}
						if (line.equals("")) {
							subtitleResult.getElements().add(element);
							isCurrentLineTime = false;
						}

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return subtitleResult;
		} else return video.map(value -> new Subtitle(value.getTitle())).orElse(null);

	}

	private double getSeconds(String text) {
		String[] timeParts = text.split(":");
		double hours = Double.parseDouble(timeParts[0]);
		double minutes = Double.parseDouble(timeParts[1]);
		String[] secondsParts = timeParts[2].split(",");
		double seconds = Double.parseDouble(secondsParts[0]);
		double secondsDecimal = Double.parseDouble(secondsParts[1]) / 1000;
		double result = (hours * 3600);
		result += minutes * 60;
		result += seconds;
		result += secondsDecimal;
		return result;
	}

	public Integer getWatchedTime(Long videoID) {
		Optional<Video> optional = videoRepository.findById(videoID);
		return optional.map(Video::getWatched).orElse(null);
	}

	public boolean setWatchedTime(Long videoID, Integer value) {
		try {
			Optional<Video> optional = videoRepository.findById(videoID);
			if (optional.isPresent()) {
				optional.get().setWatched(value);
				videoRepository.save(optional.get());
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public String getSubtitlePath(Long id) {
		Optional<Video> optionalVideo = videoRepository.findById(id);
		if (optionalVideo.isPresent() && !Objects.isNull(optionalVideo.get().getSubtitle())) {
			File vttFile = new File(optionalVideo.get().getSubtitle().getPath().replaceAll(".srt", ".vtt"));
			if (vttFile.exists()) {
				return vttFile.getPath();
			} else {
				File srtFile = new File(optionalVideo.get().getSubtitle().getPath());
				StringBuilder result = new StringBuilder("WEBVTT\n\n");
				try (BufferedReader br = new BufferedReader(new FileReader(srtFile))) {
					String line;
					while ((line = br.readLine()) != null) {
						result.append(line).append("\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				String content = result.toString();
				Path path = Paths.get(vttFile.getPath());
				try {
					Files.write(path, content.getBytes(StandardCharsets.UTF_8));
				} catch (IOException e) {
					logger.warn("File writing encountered an error, while generating VTT file for video with id: {}",
							id);
					// e.printStackTrace();
				}
			}
			return vttFile.getPath();
		} else {
			return "/innerUsed/src/main/resources/video/empty.vtt";
		}
	}
}