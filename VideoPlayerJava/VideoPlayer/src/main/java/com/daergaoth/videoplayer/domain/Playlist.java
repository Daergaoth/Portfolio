package com.daergaoth.videoplayer.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Playlist")
public class Playlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToMany
	private List<Video> listOfAllVideo = new ArrayList<>();
	
	@ManyToMany
	private List<Video> listOfFillerVideo = new ArrayList<>();
	
	@ManyToOne
	private Video lastViewedVideo;
	
	private Integer lastVideoViewedUntil;
	
	@OneToMany(mappedBy = "playlist")
	private List<FillerRecord> listOfFillerElements = new ArrayList<>();

	public Playlist(Long id, String name, List<Video> listOfAllVideo, List<Video> listOfFillerVideo,
			List<FillerRecord> listOfFillerElements, Video lastViewedVideo, Integer lastVideoViewedUntil) {
		super();
		this.id = id;
		this.name = name;
		this.listOfAllVideo = listOfAllVideo;
		this.listOfFillerVideo = listOfFillerVideo;
		this.listOfFillerElements = listOfFillerElements;
		this.lastViewedVideo = lastViewedVideo;
		this.lastVideoViewedUntil = lastVideoViewedUntil;
	}

	public Integer getLastVideoViewedUntil() {
		return lastVideoViewedUntil;
	}

	public void setLastVideoViewedUntil(Integer lastVideoViewedUntil) {
		this.lastVideoViewedUntil = lastVideoViewedUntil;
	}

	public Video getLastViewedVideo() {
		return lastViewedVideo;
	}

	public void setLastViewedVideo(Video lastViewedVideo) {
		this.lastViewedVideo = lastViewedVideo;
	}

	public Playlist() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Video> getListOfAllVideo() {
		return listOfAllVideo;
	}

	public void setListOfAllVideo(List<Video> listOfAllVideo) {
		this.listOfAllVideo = listOfAllVideo;
	}

	public List<Video> getListOfFillerVideo() {
		return listOfFillerVideo;
	}

	public void setListOfFillerVideo(List<Video> listOfFillerVideo) {
		this.listOfFillerVideo = listOfFillerVideo;
	}

	public List<FillerRecord> getListOfFillerElements() {
		return listOfFillerElements;
	}

	public void setListOfFillerElements(List<FillerRecord> listOfFillerElements) {
		this.listOfFillerElements = listOfFillerElements;
	}
}