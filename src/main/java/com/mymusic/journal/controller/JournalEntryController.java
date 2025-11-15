package com.mymusic.journal.controller;

import com.mymusic.journal.dto.request.JournalEntryRequestDTO;
import com.mymusic.journal.dto.response.JournalEntryResponseDTO;
import com.mymusic.journal.security.CustomUserDetails;
import com.mymusic.journal.service.JournalEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
@RequiredArgsConstructor
@Slf4j
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    @PostMapping
    public ResponseEntity<JournalEntryResponseDTO> createJournalEntry(
            @Valid @RequestBody JournalEntryRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        log.info("Creating journal entry for user: {}", userDetails.getUserId());
        JournalEntryResponseDTO response = journalEntryService.createJournalEntry(userDetails.getUserId(), dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<JournalEntryResponseDTO>> getAllJournalEntries(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String genre,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();
        log.info("Fetching journal entries for user: {} with filters - year: {}, city: {}, genre: {}",
                userId, year, city, genre);

        List<JournalEntryResponseDTO> entries;

        if (year != null && city != null && genre != null) {
            entries = journalEntryService.getJournalEntriesByYearAndCityAndGenre(userId, year, city, genre);
        } else if (year != null && city != null) {
            entries = journalEntryService.getJournalEntriesByYearAndCity(userId, year, city);
        } else if (year != null && genre != null) {
            entries = journalEntryService.getJournalEntriesByYearAndGenre(userId, year, genre);
        } else if (city != null && genre != null) {
            entries = journalEntryService.getJournalEntriesByCityAndGenre(userId, city, genre);
        } else if (year != null) {
            entries = journalEntryService.getJournalEntriesByYear(userId, year);
        } else if (city != null) {
            entries = journalEntryService.getJournalEntriesByCity(userId, city);
        } else if (genre != null) {
            entries = journalEntryService.getJournalEntriesByGenre(userId, genre);
        } else {
            entries = journalEntryService.getAllJournalEntries(userId);
        }

        return ResponseEntity.ok(entries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntryResponseDTO> getJournalEntryById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        log.info("Fetching journal entry ID: {} for user: {}", id, userDetails.getUserId());
        JournalEntryResponseDTO response = journalEntryService.getJournalEntryById(id, userDetails.getUserId());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntryResponseDTO> updateJournalEntry(
            @PathVariable Long id,
            @Valid @RequestBody JournalEntryRequestDTO dto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        log.info("Updating journal entry ID: {} for user: {}", id, userDetails.getUserId());
        JournalEntryResponseDTO response = journalEntryService.updateJournalEntry(id, userDetails.getUserId(), dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournalEntry(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        log.info("Deleting journal entry ID: {} for user: {}", id, userDetails.getUserId());
        journalEntryService.deleteJournalEntry(id, userDetails.getUserId());

        return ResponseEntity.noContent().build();
    }

}
