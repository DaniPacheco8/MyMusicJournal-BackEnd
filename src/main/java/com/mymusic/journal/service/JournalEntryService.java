package com.mymusic.journal.service;

import com.mymusic.journal.dto.request.JournalEntryRequestDTO;
import com.mymusic.journal.dto.response.JournalEntryResponseDTO;
import com.mymusic.journal.entity.JournalEntry;
import com.mymusic.journal.entity.Concert;
import com.mymusic.journal.entity.User;
import com.mymusic.journal.repository.JournalEntryRepository;
import com.mymusic.journal.repository.ConcertRepository;
import com.mymusic.journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;

    public JournalEntryResponseDTO createJournalEntry(Long userId, JournalEntryRequestDTO dto) {
        log.info("Creating journal entry for user: {}", userId);

        validateConcertExists(dto.getConcertId());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Concert concert = concertRepository.findById(dto.getConcertId())
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        JournalEntry journalEntry = JournalEntry.builder()
                .user(user)
                .concert(concert)
                .personalNotes(dto.getPersonalNotes())
                .rating(dto.getRating())
                .backgroundImage(dto.getBackgroundImage())
                .build();

        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        log.info("Journal entry created with ID: {}", savedEntry.getId());

        return mapToResponseDTO(savedEntry);
    }

    public JournalEntryResponseDTO getJournalEntryById(Long id, Long userId) {
        log.info("Fetching journal entry ID: {} for user: {}", id, userId);

        JournalEntry journalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal entry not found"));

        validateUserAccess(journalEntry, userId);

        return mapToResponseDTO(journalEntry);
    }

    public List<JournalEntryResponseDTO> getAllJournalEntries(Long userId) {
        log.info("Fetching all journal entries for user: {}", userId);

        List<JournalEntry> entries = journalEntryRepository.findByUserId(userId);

        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public JournalEntryResponseDTO updateJournalEntry(Long id, Long userId, JournalEntryRequestDTO dto) {
        log.info("Updating journal entry ID: {} for user: {}", id, userId);

        JournalEntry journalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal entry not found"));

        validateUserAccess(journalEntry, userId);
        validateConcertExists(dto.getConcertId());

        Concert concert = concertRepository.findById(dto.getConcertId())
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        journalEntry.setConcert(concert);
        journalEntry.setPersonalNotes(dto.getPersonalNotes());
        journalEntry.setRating(dto.getRating());
        journalEntry.setBackgroundImage(dto.getBackgroundImage());

        JournalEntry updatedEntry = journalEntryRepository.save(journalEntry);
        log.info("Journal entry ID: {} updated", updatedEntry.getId());

        return mapToResponseDTO(updatedEntry);
    }

    public void deleteJournalEntry(Long id, Long userId) {
        log.info("Deleting journal entry ID: {} for user: {}", id, userId);

        JournalEntry journalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal entry not found"));

        validateUserAccess(journalEntry, userId);

        journalEntryRepository.delete(journalEntry);
        log.info("Journal entry ID: {} deleted", id);
    }

    public List<JournalEntryResponseDTO> getJournalEntriesByYear(Long userId, Integer year) {
        log.info("Fetching journal entries for user: {} filtered by year: {}", userId, year);

        List<JournalEntry> entries = journalEntryRepository.findByUserIdAndYear(userId, year);

        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<JournalEntryResponseDTO> getJournalEntriesByCity(Long userId, String city) {
        log.info("Fetching journal entries for user: {} filtered by city: {}", userId, city);

        List<JournalEntry> entries = journalEntryRepository.findByUserIdAndCity(userId, city);

        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<JournalEntryResponseDTO> getJournalEntriesByGenre(Long userId, String genre) {
        log.info("Fetching journal entries for user: {} filtered by genre: {}", userId, genre);

        List<JournalEntry> entries = journalEntryRepository.findByUserIdAndGenre(userId, genre);

        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<JournalEntryResponseDTO> getJournalEntriesByYearAndCity(Long userId, Integer year, String city) {
        log.info("Fetching journal entries for user: {} filtered by year: {} and city: {}", userId, year, city);

        List<JournalEntry> entries = journalEntryRepository.findByUserIdAndYearAndCity(userId, year, city);

        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<JournalEntryResponseDTO> getJournalEntriesByYearAndGenre(Long userId, Integer year, String genre) {
        log.info("Fetching journal entries for user: {} filtered by year: {} and genre: {}", userId, year, genre);

        List<JournalEntry> entries = journalEntryRepository.findByUserIdAndYearAndGenre(userId, year, genre);

        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<JournalEntryResponseDTO> getJournalEntriesByCityAndGenre(Long userId, String city, String genre) {
        log.info("Fetching journal entries for user: {} filtered by city: {} and genre: {}", userId, city, genre);

        List<JournalEntry> entries = journalEntryRepository.findByUserIdAndCityAndGenre(userId, city, genre);

        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<JournalEntryResponseDTO> getJournalEntriesByYearAndCityAndGenre(Long userId, Integer year, String city,
            String genre) {
        log.info("Fetching journal entries for user: {} filtered by year: {}, city: {}, and genre: {}", userId, year,
                city, genre);

        List<JournalEntry> entries = journalEntryRepository.findByUserIdAndYearAndCityAndGenre(userId, year, city,
                genre);

        return entries.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private void validateUserAccess(JournalEntry journalEntry, Long userId) {
        if (!journalEntry.getUser().getId().equals(userId)) {
            log.warn("Unauthorized access attempt for journal entry ID: {} by user: {}", journalEntry.getId(), userId);
            throw new RuntimeException("Unauthorized: You do not have access to this journal entry");
        }
    }

    private void validateConcertExists(Long concertId) {
        if (!concertRepository.existsById(concertId)) {
            log.warn("Concert ID: {} does not exist", concertId);
            throw new RuntimeException("Concert not found with ID: " + concertId);
        }
    }

    private JournalEntryResponseDTO mapToResponseDTO(JournalEntry journalEntry) {
        Concert concert = journalEntry.getConcert();
        String concertTitle = concert.getArtist() + " at " + concert.getVenue();

        return JournalEntryResponseDTO.builder()
                .id(journalEntry.getId())
                .concertId(concert.getId())
                .concertTitle(concertTitle)
                .personalNotes(journalEntry.getPersonalNotes())
                .rating(journalEntry.getRating())
                .backgroundImage(journalEntry.getBackgroundImage())
                .createdAt(journalEntry.getCreatedAt())
                .updatedAt(journalEntry.getUpdatedAt())
                .date(concert.getDate())
                .city(concert.getCity())
                .genre(concert.getGenre())
                .build();
    }

}
