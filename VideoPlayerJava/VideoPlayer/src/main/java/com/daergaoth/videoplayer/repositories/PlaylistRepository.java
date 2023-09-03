package com.daergaoth.videoplayer.repositories;

import com.daergaoth.videoplayer.domain.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
	
	@Query("select p from Playlist p where p.name = :name")
    List<Playlist> getPlaylistByName(String name);

    @Query("select p from Playlist p where true ORDER BY p.name")
    List<Playlist> getAllPlaylistSortedByName();
}