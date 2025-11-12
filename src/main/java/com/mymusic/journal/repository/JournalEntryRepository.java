package com.mymusic.journal.repository;

import com.mymusic.journal.entity.JournalEntry;
import com.mymusic.journal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findByUser(User user);

    List<JournalEntry> findByUserId(Long userId);

    @Query("SELECT je FROM JournalEntry je WHERE je.user.id = :userId AND YEAR(je.concert.date) = :year")
    List<JournalEntry> findByUserIdAndYear(@Param("userId") Long userId, @Param("year") Integer year);

    @Query("SELECT je FROM JournalEntry je WHERE je.user.id = :userId AND je.concert.city = :city")
    List<JournalEntry> findByUserIdAndCity(@Param("userId") Long userId, @Param("city") String city);

    @Query("SELECT je FROM JournalEntry je WHERE je.user.id = :userId AND je.concert.genre = :genre")
    List<JournalEntry> findByUserIdAndGenre(@Param("userId") Long userId, @Param("genre") String genre);

    @Query("SELECT je FROM JournalEntry je WHERE je.user.id = :userId AND YEAR(je.concert.date) = :year AND je.concert.city = :city")
    List<JournalEntry> findByUserIdAndYearAndCity(@Param("userId") Long userId, @Param("year") Integer year,
            @Param("city") String city);

    @Query("SELECT je FROM JournalEntry je WHERE je.user.id = :userId AND YEAR(je.concert.date) = :year AND je.concert.genre = :genre")
    List<JournalEntry> findByUserIdAndYearAndGenre(@Param("userId") Long userId, @Param("year") Integer year,
            @Param("genre") String genre);

    @Query("SELECT je FROM JournalEntry je WHERE je.user.id = :userId AND je.concert.city = :city AND je.concert.genre = :genre")
    List<JournalEntry> findByUserIdAndCityAndGenre(@Param("userId") Long userId, @Param("city") String city,
            @Param("genre") String genre);

    @Query("SELECT je FROM JournalEntry je WHERE je.user.id = :userId AND YEAR(je.concert.date) = :year AND je.concert.city = :city AND je.concert.genre = :genre")
    List<JournalEntry> findByUserIdAndYearAndCityAndGenre(@Param("userId") Long userId, @Param("year") Integer year,
            @Param("city") String city, @Param("genre") String genre);
}