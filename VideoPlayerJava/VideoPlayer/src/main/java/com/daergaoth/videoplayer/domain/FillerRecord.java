package com.daergaoth.videoplayer.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "FillerRecord")
public class FillerRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int Start;
	
	private int end;
	
	@ManyToOne
	private Video video;
	
	@ManyToOne
	private Playlist playlist;

	public FillerRecord() {
		super();
	}

	public FillerRecord(Long id, int start, int end, Video video, Playlist playlist) {
		super();
		this.id = id;
		Start = start;
		this.end = end;
		this.video = video;
		this.playlist = playlist;
	}
	
	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
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

	public int getStart() {
		return Start;
	}

	public void setStart(int start) {
		Start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
