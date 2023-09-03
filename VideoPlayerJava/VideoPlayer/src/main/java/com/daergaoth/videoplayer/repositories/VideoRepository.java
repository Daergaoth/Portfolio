package com.daergaoth.videoplayer.repositories;

import com.daergaoth.videoplayer.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
	
	@Query("select v from Video v where v.absolutePath = :path")
    List<Video> getFolderByPath(@Param("path") String path);

    @Query("select v from Video v where v.title = :title")
    List<Video> getByTitle(@Param("title") String title);
}