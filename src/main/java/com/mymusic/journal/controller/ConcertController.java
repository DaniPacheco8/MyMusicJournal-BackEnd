package com.mymusic.journal.controller;

import com.mymusic.journal.dto.response.ConcertDTO;
import com.mymusic.journal.dto.response.ConcertMapDTO;
import com.mymusic.journal.security.JwtTokenProvider;
import com.mymusic.journal.service.ConcertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
@RequiredArgsConstructor
@Slf4j
public class ConcertController {

    private final ConcertService concertService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<List<ConcertDTO>> getAllConcerts(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String genre) {

        log.info("Fetching concerts with filters - year: {}, city: {}, genre: {}", year, city, genre);

        List<ConcertDTO> concerts;

        if (year != null && city != null && genre != null) {
            concerts = concertService.getConcertsByGenreAndCityAndYear(genre, city, year);
        } else if (year != null && city != null) {
            concerts = concertService.getConcertsByCityAndYear(city, year);
        } else if (year != null && genre != null) {
            concerts = concertService.getConcertsByGenreAndYear(genre, year);
        } else if (city != null && genre != null) {
            concerts = concertService.getConcertsByGenreAndCity(genre, city);
        } else if (year != null) {
            concerts = concertService.getConcertsByYear(year);
        } else if (city != null) {
            concerts = concertService.getConcertsByCity(city);
        } else if (genre != null) {
            concerts = concertService.getConcertsByGenre(genre);
        } else {
            concerts = concertService.getAllConcerts();
        }

        return ResponseEntity.ok(concerts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcertDTO> getConcertById(@PathVariable Long id) {
        log.info("Fetching concert ID: {}", id);
        ConcertDTO concert = concertService.getConcertById(id);
        return ResponseEntity.ok(concert);
    }

    @GetMapping("/map")
    public ResponseEntity<List<ConcertMapDTO>> getMapData(@RequestHeader("Authorization") String token) {
        log.info("Fetching map data");
        Long userId = getUserIdFromToken(token);
        List<ConcertMapDTO> mapData = concertService.getMapDataForUser(userId);
        return ResponseEntity.ok(mapData);
    }

    private Long getUserIdFromToken(String token) {
        String jwtToken = token.replace("Bearer ", "");
        return jwtTokenProvider.getUserIdFromToken(jwtToken);
    }
}