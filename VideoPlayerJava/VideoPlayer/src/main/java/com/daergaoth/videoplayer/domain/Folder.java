package com.daergaoth.videoplayer.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Folder")
public class Folder implements DomainInterfaces, FolderInterface {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String path;

	@OneToMany(mappedBy = "parentFolder")
	private List<Folder> subfolderList = new ArrayList<>();

	@ManyToOne
	private Folder parentFolder;

	@OneToMany(mappedBy = "folder")
	private List<Video> videos = new ArrayList<>();

	@OneToMany(mappedBy = "folder")
	private List<Subtitle> subtitles = new ArrayList<>();

	public Folder() {
	}

	public Folder(Long id, String name, String path, List<Video> videos, List<Subtitle> subtitles,
			List<Folder> subfolderList, Folder parentFolder) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.videos = videos;
		this.subtitles = subtitles;

		this.subfolderList = subfolderList;
		this.parentFolder = parentFolder;

	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public List<Folder> getSubfolderList() {
		return subfolderList;
	}

	public void setSubfolderList(List<Folder> subfolderList) {
		this.subfolderList = subfolderList;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<Subtitle> getSubtitles() {
		return subtitles;
	}

	public void setSubtitles(List<Subtitle> subtitles) {
		this.subtitles = subtitles;
	}
}