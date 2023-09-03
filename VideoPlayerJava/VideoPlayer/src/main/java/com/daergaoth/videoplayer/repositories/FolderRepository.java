package com.daergaoth.videoplayer.repositories;

import com.daergaoth.videoplayer.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
	
	@Query("select f from Folder f where f.path = :path")
    List<Folder> getFolderByPath(@Param("path") String path);
}