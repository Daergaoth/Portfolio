package com.daergaoth.videoplayer.helper;

public class SubtitleElement {
	private double start;
	private double end;
	private String content = "";
	
	public SubtitleElement() {}

	public SubtitleElement(double start, double end, String content) {
		super();
		this.start = start;
		this.end = end;
		this.content = content;
	}


	public double getStart() {
		return start;
	}


	public void setStart(double start) {
		this.start = start;
	}


	public double getEnd() {
		return end;
	}


	public void setEnd(double end) {
		this.end = end;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}