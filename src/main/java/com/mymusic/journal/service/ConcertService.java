package com.mymusic.journal.service;

import com.mymusic.journal.dto.response.ConcertDTO;
import com.mymusic.journal.dto.response.ConcertMapDTO;
import com.mymusic.journal.entity.Concert;
import com.mymusic.journal.mapper.ConcertMapper;
import com.mymusic.journal.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertMapper concertMapper;

    public List<ConcertDTO> getAllConcerts() {
        log.info("Fetching all concerts");
        return concertRepository.findAll()
                .stream()
                .map(concertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ConcertDTO getConcertById(Long id) {
        log.info("Fetching concert ID: {}", id);
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found with ID: " + id));
        return concertMapper.toDTO(concert);
    }

    public List<ConcertDTO> getConcertsByGenre(String genre) {
        log.info("Fetching concerts by genre: {}", genre);
        return concertRepository.findByGenreIgnoreCase(genre)
                .stream()
                .map(concertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConcertDTO> getConcertsByYear(Integer year) {
        log.info("Fetching concerts by year: {}", year);
        return concertRepository.findByYear(year)
                .stream()
                .map(concertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConcertDTO> getConcertsByCity(String city) {
        log.info("Fetching concerts by city: {}", city);
        return concertRepository.findByCityIgnoreCase(city)
                .stream()
                .map(concertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConcertDTO> getConcertsByGenreAndYear(String genre, Integer year) {
        log.info("Fetching concerts by genre: {} and year: {}", genre, year);
        return concertRepository.findByGenreIgnoreCaseAndYear(genre, year)
                .stream()
                .map(concertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConcertDTO> getConcertsByGenreAndCity(String genre, String city) {
        log.info("Fetching concerts by genre: {} and city: {}", genre, city);
        return concertRepository.findByGenreIgnoreCaseAndCityIgnoreCase(genre, city)
                .stream()
                .map(concertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConcertDTO> getConcertsByCityAndYear(String city, Integer year) {
        log.info("Fetching concerts by city: {} and year: {}", city, year);
        return concertRepository.findByCityIgnoreCaseAndYear(city, year)
                .stream()
                .map(concertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConcertDTO> getConcertsByGenreAndCityAndYear(String genre, String city, Integer year) {
        log.info("Fetching concerts by genre: {}, city: {}, and year: {}", genre, city, year);
        return concertRepository.findByGenreIgnoreCaseAndCityIgnoreCaseAndYear(genre, city, year)
                .stream()
                .map(concertMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConcertMapDTO> getMapDataForUser(Long userId) {
        log.info("Fetching concert map data for user: {}", userId);

        List<Concert> concerts = concertRepository.findConcertsAttendedByUser(userId);

        return concerts.stream()
                .map(concert -> ConcertMapDTO.builder()
                        .id(concert.getId())
                        .artist(concert.getArtist())
                        .city(concert.getCity())
                        .latitude(concert.getLatitude())
                        .longitude(concert.getLongitude())
                        .build())
                .collect(Collectors.toList());
    }
}
