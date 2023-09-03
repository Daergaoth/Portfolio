package com.daergaoth.videoplayer.dto;

import com.daergaoth.videoplayer.domain.Video;

import java.util.Objects;

public class VideoMainListDTO {
	
	private Long id;
	
	private String title;
	
	private Boolean hasSubtitle;
	
	private Integer watched;
	
	public VideoMainListDTO() {}
	
	public VideoMainListDTO(Video video) {
		this.id = video.getId();
		this.title = video.getTitle();
		this.watched = video.getWatched();
		this.hasSubtitle = !Objects.isNull(video.getSubtitle());
	}

	public VideoMainListDTO(Long id, String title, Boolean hasSubtitle) {
		super();
		this.id = id;
		this.title = title;
		this.hasSubtitle = hasSubtitle;
	}
	
	public Integer getWatched() {
		return watched;
	}

	public void setWatched(Integer watched) {
		this.watched = watched;
	}

	public Boolean getHasSubtitle() {
		return hasSubtitle;
	}

	public void setHasSubtitle(Boolean hasSubtitle) {
		this.hasSubtitle = hasSubtitle;
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
	
}