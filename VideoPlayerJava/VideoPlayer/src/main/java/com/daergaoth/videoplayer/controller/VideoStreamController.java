package com.daergaoth.videoplayer.controller;

import com.daergaoth.videoplayer.dto.Subtitle;
import com.daergaoth.videoplayer.service.VideoStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/video")
public class VideoStreamController {

	private final VideoStreamService videoStreamService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public VideoStreamController(VideoStreamService videoStreamService) {
		this.videoStreamService = videoStreamService;
	}

	@CrossOrigin
	@GetMapping(value = "/stream/{id}", produces = {"video/mkv", "video/mp4"})
	public Mono<Resource> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList, @PathVariable("id") Long id, HttpServletRequest request) {
		logger.debug("Requesting video with id: {} from ip address: {} with range: {}", id, request.getRemoteAddr(), httpRangeList);
		Mono<Resource> result = videoStreamService.prepareContent(id);
		if (!Objects.isNull(result)) {
			return result;
		} else {
			return null;
		}
	}

	private static Object fromByteArrayToObject(byte[] byteArr) throws IOException, ClassNotFoundException {
		if (Objects.nonNull(byteArr)) {
			ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
			ObjectInput in = new ObjectInputStream(bis);
			return in.readObject();
		}
		return null;
	}

	@CrossOrigin
	@GetMapping("/subtitles/{id}")
	public ResponseEntity<Subtitle> downloadFileFromLocal(@PathVariable("id") Long videoID) {
		Subtitle subtitle = videoStreamService.getSubtitle(videoID);
		if (!Objects.isNull(subtitle)) {
			return new ResponseEntity<Subtitle>(subtitle, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}
	
	@CrossOrigin
	@GetMapping("/watched/{id}")
	public ResponseEntity<Integer> getVideoWatchedTime(@PathVariable("id") Long videoID) {
		Integer result = videoStreamService.getWatchedTime(videoID);
		if (!Objects.isNull(result)) {
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}
	
	@CrossOrigin
	@GetMapping("/watched/{id}/{value}")
	public ResponseEntity<Void> setVideoWatchedTime(@PathVariable("id") Long videoID, @PathVariable("value") Integer value) {
		boolean result = videoStreamService.setWatchedTime(videoID, value);
		if (result) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@CrossOrigin
	@GetMapping("/subtitlesForPhone/{id}")
	public ResponseEntity<Subtitle> downloadFileFromLocalForPhone(@PathVariable("id") Long videoID) {
		Subtitle subtitle = videoStreamService.getSubtitleForPhone(videoID);
		if (!Objects.isNull(subtitle)) {
			return new ResponseEntity<Subtitle>(subtitle, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}
	
	@CrossOrigin
	@GetMapping("/subtitlesFile/{id}")
	public ResponseEntity getSubtitleFile(@PathVariable("id") Long id) {
		String subtitlePath = videoStreamService.getSubtitlePath(id);
		Path path = Paths.get(subtitlePath);
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.ALL)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}