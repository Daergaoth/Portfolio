package com.daergaoth.videoplayer.domain;

import jakarta.persistence.*;

@Entity
@Table(name="Subtitle")
public class Subtitle implements DomainInterfaces {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	private Folder folder;
	
	private String path;
	
	private String extension;
	
	private String title;
	
	private String fullName;
	
	@OneToOne
	private Video video;
	
	public Subtitle() {}

	public Subtitle(Long id, Folder folder, String path, String extension, String title, String fullName, Video video) {
		super();
		this.id = id;
		this.folder = folder;
		this.path = path;
		this.extension = extension;
		this.title = title;
		this.fullName = fullName;
		this.video = video;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}