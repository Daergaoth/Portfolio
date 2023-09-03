package com.daergaoth.videoplayer.repositories;

import com.daergaoth.videoplayer.domain.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtitleRepository extends JpaRepository<Subtitle, Long> {
	
	@Query("select s from Subtitle s where s.path = :path")
    List<Subtitle> getFolderByPath(@Param("path") String path);
}