package com.daergaoth.videoplayer.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Video")
public class Video implements DomainInterfaces {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String title;
	
	private String absolutePath;
	
	private String fullName;
	
	private String extension;
	
	private Integer watched = 0;
	
	@ManyToMany(mappedBy = "listOfAllVideo")
	private List<Playlist> playLists = new ArrayList<>();
	
	@ManyToMany(mappedBy = "listOfFillerVideo")
	private List<Playlist> playListFillers = new ArrayList<>();
	
	@OneToMany(mappedBy = "video")
	private List<FillerRecord> listOfFillerParts = new ArrayList<>();
	
	@OneToMany(mappedBy = "lastViewedVideo")
	private List<Playlist> endOfPlayLists = new ArrayList<>();
	
	@OneToOne
	private Subtitle subtitle;
	
	@ManyToOne
	private Folder folder;
	
	public Video() {}

	public Video(Long id, String title, String absolutePath, String fullName, String extension, Folder folder, Subtitle subtitle, Integer watched,
			List<Playlist> playLists, List<FillerRecord> listOfFillerParts, List<Playlist> endOfPlayLists) {
		super();
		this.id = id;
		this.title = title;
		this.absolutePath = absolutePath;
		this.fullName = fullName;
		this.extension = extension;
		this.folder = folder;
		this.subtitle = subtitle;
		this.watched = watched;
		this.playLists = playLists;
		this.endOfPlayLists = endOfPlayLists;
		this.listOfFillerParts = listOfFillerParts;
	}

	public List<Playlist> getEndOfPlayLists() {
		return endOfPlayLists;
	}

	public void setEndOfPlayLists(List<Playlist> endOfPlayLists) {
		this.endOfPlayLists = endOfPlayLists;
	}

	public List<Playlist> getPlayListFillers() {
		return playListFillers;
	}

	public void setPlayListFillers(List<Playlist> playListFillers) {
		this.playListFillers = playListFillers;
	}

	public List<FillerRecord> getListOfFillerParts() {
		return listOfFillerParts;
	}

	public void setListOfFillerParts(List<FillerRecord> listOfFillerParts) {
		this.listOfFillerParts = listOfFillerParts;
	}

	public List<Playlist> getPlayLists() {
		return playLists;
	}

	public void setPlayLists(List<Playlist> playLists) {
		this.playLists = playLists;
	}

	public Integer getWatched() {
		return watched;
	}

	public void setWatched(Integer watched) {
		this.watched = watched;
	}

	public Subtitle getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(Subtitle subtitle) {
		this.subtitle = subtitle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}
}
