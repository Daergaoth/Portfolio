package com.daergaoth.videoplayer.controller;

import com.daergaoth.videoplayer.domain.Playlist;
import com.daergaoth.videoplayer.dto.FolderDTO;
import com.daergaoth.videoplayer.dto.MainListDTO;
import com.daergaoth.videoplayer.dto.PlaylistDTO;
import com.daergaoth.videoplayer.service.FolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/folders")
public class FolderController {
	
	@Autowired
	private final FolderService folderService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public FolderController(FolderService folderService) {
		super();
		this.folderService = folderService;
	}

	@CrossOrigin
	@GetMapping("/all")
	public ResponseEntity<MainListDTO> getAllFolderWithContent() {
		return new ResponseEntity<>(new MainListDTO(folderService.getAllFolderWithContent()), HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public ResponseEntity<FolderDTO> getFolderById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(folderService.getFolderById(id), HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping("/playlist")
	public ResponseEntity<Void> createPlaylist(@RequestBody PlaylistDTO playlistDTO) {
		Playlist result = folderService.createPlaylist(playlistDTO);
		return Objects.nonNull(result) ? new ResponseEntity<>( HttpStatus.OK) : new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*@CrossOrigin
	@GetMapping("/playlist/{id}")
	public ResponseEntity<PlaylistDTO> getPlaylistByID(@PathVariable("id") Long id) {
		PlaylistDTO result = folderService.getPlaylist(id);
		return Objects.isNull(result) ? new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<PlaylistDTO>(result, HttpStatus.OK);
	}*/

	@CrossOrigin
	@GetMapping("/playlist/narutoFillerFree/")
	public ResponseEntity<Void> createNarutoFillerFreePlaylist() {
		Playlist result = folderService.createNarutoFillerFreePlaylist();
		return Objects.nonNull(result) ? new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@CrossOrigin
	@GetMapping("/playlist")
	public ResponseEntity<List<PlaylistDTO>> getAllPlaylistByID() {
		List<PlaylistDTO> result = folderService.getAllPlaylist();
		//logger.debug(result.stream().map((e) -> {return e.getId();}).collect(Collectors.toList()).toString());
		return Objects.isNull(result) ? new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR) : new ResponseEntity<List<PlaylistDTO>>(result, HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/playlistReset/{id}")
	public ResponseEntity<Void> resetPlaylistByID(@PathVariable("id") Long id) {
		Playlist result = folderService.resetPlaylist(id);
		return Objects.nonNull(result) ? new ResponseEntity<>( HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@CrossOrigin
	@GetMapping("/playlistUpdateState/{id}/{videoID}")
	public ResponseEntity<Void> updateLastWatchedVideoInPlaylist(@PathVariable("id") Long id, @PathVariable("videoID") Long videoID) {
		Playlist result = folderService.updateLastWatchedVideoInPlaylist(id, videoID);
		return Objects.nonNull(result) ? new ResponseEntity<>( HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}