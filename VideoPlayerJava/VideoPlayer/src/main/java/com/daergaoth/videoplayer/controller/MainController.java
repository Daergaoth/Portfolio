package com.daergaoth.videoplayer.controller;

import com.daergaoth.videoplayer.helper.MemoryStats;
import com.daergaoth.videoplayer.service.FileSystemReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/main")
public class MainController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private final FileSystemReaderService fileSystemReaderService;
	
	
	
	public MainController(FileSystemReaderService fileSystemReaderService) {
		super();
		this.fileSystemReaderService = fileSystemReaderService;
	}
	
	@CrossOrigin
	@GetMapping("/memory-status")
	public MemoryStats getMemoryStatistics() {
	    MemoryStats stats = new MemoryStats();
	    stats.setHeapSize(Runtime.getRuntime().totalMemory());
	    stats.setHeapMaxSize(Runtime.getRuntime().maxMemory());
	    stats.setHeapFreeSize(Runtime.getRuntime().freeMemory());
	    return stats;
	}
	
	@CrossOrigin
	@GetMapping()
	public ResponseEntity<Void> refreshDatabase() {
		logger.debug("Database update started.");
	    return fileSystemReaderService.resolveFiles() ?  new ResponseEntity<Void>(HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@CrossOrigin
	@GetMapping("/gc")
	public ResponseEntity<Void> callgc() {
		try {
			Runtime.getRuntime().gc();
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}