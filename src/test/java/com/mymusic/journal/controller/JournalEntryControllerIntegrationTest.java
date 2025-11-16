package com.mymusic.journal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymusic.journal.dto.request.JournalEntryRequestDTO;
import com.mymusic.journal.entity.Concert;
import com.mymusic.journal.entity.JournalEntry;
import com.mymusic.journal.entity.User;
import com.mymusic.journal.repository.ConcertRepository;
import com.mymusic.journal.repository.JournalEntryRepository;
import com.mymusic.journal.repository.UserRepository;
import com.mymusic.journal.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("JournalEntryController Integration Tests")
public class JournalEntryControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ConcertRepository concertRepository;

        @Autowired
        private JournalEntryRepository journalEntryRepository;

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @Autowired
        private PasswordEncoder passwordEncoder;

        private User testUser;
        private Concert testConcert;
        private String token;

        @BeforeEach
        void setUp() {
                journalEntryRepository.deleteAll();
                concertRepository.deleteAll();
                userRepository.deleteAll();

                testUser = User.builder()
                                .email("integration@test.com")
                                .password(passwordEncoder.encode("password123"))
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();
                testUser = userRepository.save(testUser);

                token = jwtTokenProvider.generateToken(testUser.getId(), testUser.getEmail());

                testConcert = Concert.builder()
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
                testConcert = concertRepository.save(testConcert);
        }

        @Test
        @DisplayName("POST /api/journal - Should create journal entry with real database")
        void testCreateJournalEntry_IntegrationTest() throws Exception {
                JournalEntryRequestDTO requestDTO = JournalEntryRequestDTO.builder()
                                .concertId(testConcert.getId())
                                .personalNotes("This was an absolutely amazing concert experience!")
                                .build();

                mockMvc.perform(post("/api/journal")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDTO)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id", notNullValue()))
                                .andExpect(jsonPath("$.concertId", is(testConcert.getId().intValue())))
                                .andExpect(jsonPath("$.concertTitle", containsString("The Beatles")))
                                .andExpect(jsonPath("$.personalNotes",
                                                is("This was an absolutely amazing concert experience!")));

                assert journalEntryRepository.count() == 1;
        }

        @Test
        @DisplayName("GET /api/journal - Should retrieve all entries from database")
        void testGetAllJournalEntries_IntegrationTest() throws Exception {
                JournalEntry entry = JournalEntry.builder()
                                .user(testUser)
                                .concert(testConcert)
                                .personalNotes("Great concert!")
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();
                journalEntryRepository.save(entry);

                mockMvc.perform(get("/api/journal")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)))
                                .andExpect(jsonPath("$[0].concertTitle", containsString("The Beatles")))
                                .andExpect(jsonPath("$[0].personalNotes", is("Great concert!")));
        }

        @Test
        @DisplayName("GET /api/journal?city=London - Should filter entries by city from database")
        void testGetJournalEntriesWithCityFilter_IntegrationTest() throws Exception {
                JournalEntry entry = JournalEntry.builder()
                                .user(testUser)
                                .concert(testConcert)
                                .personalNotes("London concert")
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();
                journalEntryRepository.save(entry);

                mockMvc.perform(get("/api/journal?city=London")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)))
                                .andExpect(jsonPath("$[0].personalNotes", is("London concert")));
        }

}
