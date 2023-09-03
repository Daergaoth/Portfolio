package com.daergaoth.videoplayer.dto;

import java.util.ArrayList;
import java.util.List;

public class MainListDTO {
	private List<FolderDTO> folderDTOList = new ArrayList<>();

	public MainListDTO() {}
	
	public MainListDTO(List<FolderDTO> folderDTOList) {
		super();
		this.folderDTOList = folderDTOList;
	}

	public List<FolderDTO> getFolderDTOList() {
		return folderDTOList;
	}

	public void setFolderDTOList(List<FolderDTO> folderDTOList) {
		this.folderDTOList = folderDTOList;
	}
	
	
}