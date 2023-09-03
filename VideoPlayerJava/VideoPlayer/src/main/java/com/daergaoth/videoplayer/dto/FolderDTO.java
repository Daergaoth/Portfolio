package com.daergaoth.videoplayer.dto;

import com.daergaoth.videoplayer.domain.Folder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FolderDTO {
	private Long id;
	
	private String name;
	
	private String path;
	
	private List<VideoMainListDTO> videos = new ArrayList<>();
	
	public FolderDTO() {}
	
	public FolderDTO(Folder folder) {
		this.id = folder.getId();
		this.name = folder.getName();
		this.path = folder.getPath();
		this.videos = folder.getVideos().stream().map(VideoMainListDTO :: new).collect(Collectors.toList());
	}

	public FolderDTO(Long id, String name, String path, List<VideoMainListDTO> videos) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.videos = videos;
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

	public List<VideoMainListDTO> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoMainListDTO> videos) {
		this.videos = videos;
	}
}