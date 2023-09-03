package com.daergaoth.videoplayer.dto;

import com.daergaoth.videoplayer.domain.FillerRecord;

public class FillerElementDTO {
	
	private Long videoID;
	
	private int start;
	
	private int end;

	public FillerElementDTO() {
		super();
	}
	
	public FillerElementDTO(FillerRecord fillerRecord) {
		super();
		this.videoID = fillerRecord.getVideo().getId();
		this.start = fillerRecord.getStart();
		this.end = fillerRecord.getEnd();
	}

	public FillerElementDTO(Long videoID, int start, int end) {
		super();
		this.videoID = videoID;
		this.start = start;
		this.end = end;
	}

	public Long getVideoID() {
		return videoID;
	}

	public void setVideoID(Long videoID) {
		this.videoID = videoID;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
}
