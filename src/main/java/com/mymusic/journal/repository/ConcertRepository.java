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

    List<Concert> findByGenreIgnoreCase(String genre);

    List<Concert> findByCityIgnoreCase(String city);

    @Query("SELECT c FROM Concert c WHERE YEAR(c.date) = :year")
    List<Concert> findByYear(Integer year);

    @Query("SELECT c FROM Concert c WHERE UPPER(c.genre) = UPPER(:genre) AND YEAR(c.date) = :year")
    List<Concert> findByGenreIgnoreCaseAndYear(String genre, Integer year);

    @Query("SELECT c FROM Concert c WHERE UPPER(c.genre) = UPPER(:genre) AND UPPER(c.city) = UPPER(:city)")
    List<Concert> findByGenreIgnoreCaseAndCityIgnoreCase(String genre, String city);

    @Query("SELECT c FROM Concert c WHERE UPPER(c.city) = UPPER(:city) AND YEAR(c.date) = :year")
    List<Concert> findByCityIgnoreCaseAndYear(String city, Integer year);

    @Query("SELECT c FROM Concert c WHERE UPPER(c.genre) = UPPER(:genre) AND UPPER(c.city) = UPPER(:city) AND YEAR(c.date) = :year")
    List<Concert> findByGenreIgnoreCaseAndCityIgnoreCaseAndYear(String genre, String city, Integer year);

    @Query("SELECT DISTINCT c.city FROM Concert c ORDER BY c.city")
    List<String> findAllCities();

    @Query("SELECT DISTINCT c.genre FROM Concert c ORDER BY c.genre")
    List<String> findAllGenres();

    @Query("SELECT c FROM Concert c WHERE c.id IN (SELECT DISTINCT je.concert.id FROM JournalEntry je WHERE je.user.id = :userId)")
    List<Concert> findConcertsAttendedByUser(Long userId);
}