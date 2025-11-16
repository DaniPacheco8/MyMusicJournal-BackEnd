package com.mymusic.journal.service;

import com.mymusic.journal.dto.request.JournalEntryRequestDTO;
import com.mymusic.journal.dto.response.JournalEntryResponseDTO;
import com.mymusic.journal.entity.Concert;
import com.mymusic.journal.entity.JournalEntry;
import com.mymusic.journal.entity.User;
import com.mymusic.journal.repository.ConcertRepository;
import com.mymusic.journal.repository.JournalEntryRepository;
import com.mymusic.journal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JournalEntryService Unit Tests")
public class JournalEntryServiceTest {

    @Mock
    private JournalEntryRepository journalEntryRepository;

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JournalEntryService journalEntryService;

    private User testUser;
    private Concert testConcert;
    private JournalEntry testJournalEntry;
    private JournalEntryRequestDTO testRequestDTO;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("hashedPassword")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testConcert = Concert.builder()
                .id(1L)
                .artist("The Beatles")
                .venue("Abbey Road Studio")
                .city("London")
                .date(LocalDateTime.of(2024, 6, 15, 19, 0))
                .genre("Rock")
                .rating(5)
                .notes("Iconic concert")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testJournalEntry = JournalEntry.builder()
                .id(1L)
                .user(testUser)
                .concert(testConcert)
                .personalNotes("Amazing concert experience!")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testRequestDTO = JournalEntryRequestDTO.builder()
                .concertId(1L)
                .personalNotes("Amazing concert experience!")
                .build();
    }

    @Test
    @DisplayName("Should create journal entry successfully")
    void testCreateJournalEntry_Success() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(concertRepository.existsById(1L)).thenReturn(true);
        when(concertRepository.findById(1L)).thenReturn(Optional.of(testConcert));
        when(journalEntryRepository.save(any(JournalEntry.class))).thenReturn(testJournalEntry);

        JournalEntryResponseDTO result = journalEntryService.createJournalEntry(userId, testRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getConcertId()).isEqualTo(1L);
        assertThat(result.getPersonalNotes()).isEqualTo("Amazing concert experience!");
        assertThat(result.getConcertTitle()).isEqualTo("The Beatles at Abbey Road Studio");

        verify(userRepository, times(1)).findById(userId);
        verify(concertRepository, times(1)).existsById(1L);
        verify(concertRepository, times(1)).findById(1L);
        verify(journalEntryRepository, times(1)).save(any(JournalEntry.class));
    }

    @Test
    @DisplayName("Should throw exception when concert not found during creation")
    void testCreateJournalEntry_ConcertNotFound() {
        Long userId = 1L;
        when(concertRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> journalEntryService.createJournalEntry(userId, testRequestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Concert not found");

        verify(journalEntryRepository, never()).save(any(JournalEntry.class));
    }

    @Test
    @DisplayName("Should get journal entry by ID successfully")
    void testGetJournalEntryById_Success() {
        Long entryId = 1L;
        Long userId = 1L;
        when(journalEntryRepository.findById(entryId)).thenReturn(Optional.of(testJournalEntry));

        JournalEntryResponseDTO result = journalEntryService.getJournalEntryById(entryId, userId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getConcertTitle()).isEqualTo("The Beatles at Abbey Road Studio");

        verify(journalEntryRepository, times(1)).findById(entryId);
    }

    @Test
    @DisplayName("Should throw exception when user not authorized to access entry")
    void testGetJournalEntryById_Unauthorized() {
        Long entryId = 1L;
        Long unauthorizedUserId = 999L;
        when(journalEntryRepository.findById(entryId)).thenReturn(Optional.of(testJournalEntry));

        assertThatThrownBy(() -> journalEntryService.getJournalEntryById(entryId, unauthorizedUserId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unauthorized");

        verify(journalEntryRepository, times(1)).findById(entryId);
    }

    @Test
    @DisplayName("Should update journal entry successfully")
    void testUpdateJournalEntry_Success() {
        Long entryId = 1L;
        Long userId = 1L;
        JournalEntryRequestDTO updateDTO = JournalEntryRequestDTO.builder()
                .concertId(1L)
                .personalNotes("Updated notes - even better concert!")
                .build();

        JournalEntry updatedEntry = JournalEntry.builder()
                .id(1L)
                .user(testUser)
                .concert(testConcert)
                .personalNotes("Updated notes - even better concert!")
                .createdAt(testJournalEntry.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        when(journalEntryRepository.findById(entryId)).thenReturn(Optional.of(testJournalEntry));
        when(concertRepository.existsById(1L)).thenReturn(true);
        when(concertRepository.findById(1L)).thenReturn(Optional.of(testConcert));
        when(journalEntryRepository.save(any(JournalEntry.class))).thenReturn(updatedEntry);

        JournalEntryResponseDTO result = journalEntryService.updateJournalEntry(entryId, userId, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getPersonalNotes()).isEqualTo("Updated notes - even better concert!");

        verify(journalEntryRepository, times(1)).findById(entryId);
        verify(concertRepository, times(1)).existsById(1L);
        verify(journalEntryRepository, times(1)).save(any(JournalEntry.class));
    }

    @Test
    @DisplayName("Should delete journal entry successfully")
    void testDeleteJournalEntry_Success() {
        Long entryId = 1L;
        Long userId = 1L;
        when(journalEntryRepository.findById(entryId)).thenReturn(Optional.of(testJournalEntry));

        journalEntryService.deleteJournalEntry(entryId, userId);

        verify(journalEntryRepository, times(1)).delete(testJournalEntry);
    }

    @Test
    @DisplayName("Should get all journal entries for user")
    void testGetAllJournalEntries_Success() {
        Long userId = 1L;
        List<JournalEntry> entries = Arrays.asList(testJournalEntry);
        when(journalEntryRepository.findByUserId(userId)).thenReturn(entries);

        List<JournalEntryResponseDTO> result = journalEntryService.getAllJournalEntries(userId);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);

        verify(journalEntryRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should filter journal entries by year")
    void testGetJournalEntriesByYear_Success() {
        Long userId = 1L;
        Integer year = 2024;
        List<JournalEntry> entries = Arrays.asList(testJournalEntry);
        when(journalEntryRepository.findByUserIdAndYear(userId, year)).thenReturn(entries);

        List<JournalEntryResponseDTO> result = journalEntryService.getJournalEntriesByYear(userId, year);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);

        verify(journalEntryRepository, times(1)).findByUserIdAndYear(userId, year);
    }

    @Test
    @DisplayName("Should filter journal entries by city")
    void testGetJournalEntriesByCity_Success() {
        Long userId = 1L;
        String city = "London";
        List<JournalEntry> entries = Arrays.asList(testJournalEntry);
        when(journalEntryRepository.findByUserIdAndCity(userId, city)).thenReturn(entries);

        List<JournalEntryResponseDTO> result = journalEntryService.getJournalEntriesByCity(userId, city);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);

        verify(journalEntryRepository, times(1)).findByUserIdAndCity(userId, city);
    }

    @Test
    @DisplayName("Should filter journal entries by genre")
    void testGetJournalEntriesByGenre_Success() {
        Long userId = 1L;
        String genre = "Rock";
        List<JournalEntry> entries = Arrays.asList(testJournalEntry);
        when(journalEntryRepository.findByUserIdAndGenre(userId, genre)).thenReturn(entries);

        List<JournalEntryResponseDTO> result = journalEntryService.getJournalEntriesByGenre(userId, genre);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);

        verify(journalEntryRepository, times(1)).findByUserIdAndGenre(userId, genre);
    }

    @Test
    @DisplayName("Should filter journal entries by year and city")
    void testGetJournalEntriesByYearAndCity_Success() {
        Long userId = 1L;
        Integer year = 2024;
        String city = "London";
        List<JournalEntry> entries = Arrays.asList(testJournalEntry);
        when(journalEntryRepository.findByUserIdAndYearAndCity(userId, year, city)).thenReturn(entries);

        List<JournalEntryResponseDTO> result = journalEntryService.getJournalEntriesByYearAndCity(userId, year, city);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);

        verify(journalEntryRepository, times(1)).findByUserIdAndYearAndCity(userId, year, city);
    }

    @Test
    @DisplayName("Should filter journal entries by year, city and genre")
    void testGetJournalEntriesByYearAndCityAndGenre_Success() {
        Long userId = 1L;
        Integer year = 2024;
        String city = "London";
        String genre = "Rock";
        List<JournalEntry> entries = Arrays.asList(testJournalEntry);
        when(journalEntryRepository.findByUserIdAndYearAndCityAndGenre(userId, year, city, genre))
                .thenReturn(entries);

        List<JournalEntryResponseDTO> result = journalEntryService.getJournalEntriesByYearAndCityAndGenre(userId, year,
                city, genre);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);

        verify(journalEntryRepository, times(1)).findByUserIdAndYearAndCityAndGenre(userId, year, city, genre);
    }

}
