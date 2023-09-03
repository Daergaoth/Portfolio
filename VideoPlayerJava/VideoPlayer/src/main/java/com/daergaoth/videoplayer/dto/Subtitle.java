package com.daergaoth.videoplayer.dto;

import com.daergaoth.videoplayer.helper.SubtitleElement;

import java.util.ArrayList;
import java.util.List;


public class Subtitle {
	private String title;
	private String language;
	private List<SubtitleElement> elements = new ArrayList<>();
	
	public Subtitle(String title, String language, List<SubtitleElement> elements) {
		super();
		this.title = title;
		this.language = language;
		this.elements = elements;
	}
	
	public Subtitle(String title) {
		this.title = title;
	}
	
	public Subtitle() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<SubtitleElement> getElements() {
		return elements;
	}

	public void setElements(List<SubtitleElement> elements) {
		this.elements = elements;
	}
	
	
}