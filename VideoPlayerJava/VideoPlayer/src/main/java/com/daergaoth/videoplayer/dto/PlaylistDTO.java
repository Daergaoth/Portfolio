/**
 * 
 */
package com.daergaoth.videoplayer.dto;

import com.daergaoth.videoplayer.domain.Playlist;
import com.daergaoth.videoplayer.domain.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author segye
 *
 */
public class PlaylistDTO {
	
	private String name;
	
	private Long id;
	
	private List<Long> listOfAllVideo = new ArrayList<>();
	
	private List<Long> listOfFillerVideo = new ArrayList<>();
	
	private List<FillerElementDTO> listOfFillerElements = new ArrayList<>();
	
	private Long lastViewedVideo;
	
	private Integer lastVideoViewedUntil;

	public PlaylistDTO(String name, List<Long> listOfAllVideo, List<Long> listOfFillerVideo,
			List<FillerElementDTO> listOfFillerElements, Long id) {
		super();
		this.name = name;
		this.listOfAllVideo = listOfAllVideo;
		this.listOfFillerVideo = listOfFillerVideo;
		this.listOfFillerElements = listOfFillerElements;
		this.id = id;
	}

	public PlaylistDTO(Playlist playlist) {
		this.name = playlist.getName();
		this.listOfAllVideo = playlist.getListOfAllVideo().stream().map(Video::getId).collect(Collectors.toList());
		this.listOfFillerVideo = playlist.getListOfFillerVideo().stream().map(Video::getId).collect(Collectors.toList());
		this.listOfFillerElements = playlist.getListOfFillerElements().stream().map(FillerElementDTO::new).collect(Collectors.toList());
		this.lastViewedVideo = playlist.getLastViewedVideo().getId();
		this.lastVideoViewedUntil = playlist.getLastVideoViewedUntil();
		this.id = playlist.getId();
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLastViewedVideo() {
		return lastViewedVideo;
	}

	public void setLastViewedVideo(Long lastViewedVideo) {
		this.lastViewedVideo = lastViewedVideo;
	}

	public Integer getLastVideoViewedUntil() {
		return lastVideoViewedUntil;
	}

	public void setLastVideoViewedUntil(Integer lastVideoViewedUntil) {
		this.lastVideoViewedUntil = lastVideoViewedUntil;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getListOfAllVideo() {
		return listOfAllVideo;
	}

	public void setListOfAllVideo(List<Long> listOfAllVideo) {
		this.listOfAllVideo = listOfAllVideo;
	}

	public List<Long> getListOfFillerVideo() {
		return listOfFillerVideo;
	}

	public void setListOfFillerVideo(List<Long> listOfFillerVideo) {
		this.listOfFillerVideo = listOfFillerVideo;
	}

	public List<FillerElementDTO> getListOfFillerElements() {
		return listOfFillerElements;
	}

	public void setListOfFillerElements(List<FillerElementDTO> listOfFillerElements) {
		this.listOfFillerElements = listOfFillerElements;
	}
}
