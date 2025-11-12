package com.mymusic.journal.repository;

import com.mymusic.journal.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {

    List<Concert> findByArtist(String artist);

    List<Concert> findByCity(String city);

    List<Concert> findByGenre(String genre);

    @Query("SELECT DISTINCT c.city FROM Concert c ORDER BY c.city")
    List<String> findAllCities();

    @Query("SELECT DISTINCT c.genre FROM Concert c ORDER BY c.genre")
    List<String> findAllGenres();
}