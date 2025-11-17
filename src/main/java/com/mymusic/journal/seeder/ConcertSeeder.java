package com.mymusic.journal.seeder;

import com.mymusic.journal.entity.Concert;
import com.mymusic.journal.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConcertSeeder implements CommandLineRunner {

        private final ConcertRepository concertRepository;

        @Override
        public void run(String... args) throws Exception {
                if (concertRepository.count() == 0) {
                        log.info("Inserting seed concerts data...");
                        List<Concert> concerts = createConcerts();
                        concertRepository.saveAll(concerts);
                        log.info("Successfully inserted {} concerts", concerts.size());
                } else {
                        log.info("Concerts already exist in database, skipping seed");
                }
        }

        private List<Concert> createConcerts() {
                return Arrays.asList(
                                Concert.builder()
                                                .artist("The Beatles")
                                                .venue("Abbey Road Studio")
                                                .city("London")
                                                .date(LocalDateTime.of(1969, 9, 26, 19, 0))
                                                .genre("Rock")
                                                .latitude(51.5390)
                                                .longitude(-0.1789)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("Pink Floyd")
                                                .venue("Pompeii Amphitheater")
                                                .city("Naples")
                                                .date(LocalDateTime.of(1971, 8, 4, 20, 0))
                                                .genre("Rock")
                                                .latitude(40.7505)
                                                .longitude(14.5013)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("Queen")
                                                .venue("Wembley Stadium")
                                                .city("London")
                                                .date(LocalDateTime.of(1985, 7, 13, 19, 30))
                                                .genre("Rock")
                                                .latitude(51.5563)
                                                .longitude(-0.2829)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("David Bowie")
                                                .venue("Madison Square Garden")
                                                .city("New York")
                                                .date(LocalDateTime.of(1983, 6, 17, 20, 0))
                                                .genre("Rock")
                                                .latitude(40.7505)
                                                .longitude(-73.9934)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("U2")
                                                .venue("The Sphere")
                                                .city("Las Vegas")
                                                .date(LocalDateTime.of(2024, 5, 10, 20, 0))
                                                .genre("Rock")
                                                .latitude(36.1699)
                                                .longitude(-115.1398)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("Jimi Hendrix")
                                                .venue("Monterey International Pop Festival")
                                                .city("Monterey")
                                                .date(LocalDateTime.of(1967, 6, 18, 21, 0))
                                                .genre("Rock")
                                                .latitude(36.6002)
                                                .longitude(-121.8863)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("The Rolling Stones")
                                                .venue("Hyde Park")
                                                .city("London")
                                                .date(LocalDateTime.of(1969, 7, 5, 14, 0))
                                                .genre("Rock")
                                                .latitude(51.5066)
                                                .longitude(-0.1655)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("Led Zeppelin")
                                                .venue("Knebworth House")
                                                .city("Hertfordshire")
                                                .date(LocalDateTime.of(1979, 8, 4, 14, 0))
                                                .genre("Rock")
                                                .latitude(51.8172)
                                                .longitude(-0.2089)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("The Who")
                                                .venue("Max Yasgur's Farm")
                                                .city("Bethel")
                                                .date(LocalDateTime.of(1969, 8, 15, 22, 0))
                                                .genre("Rock")
                                                .latitude(41.7060)
                                                .longitude(-73.8995)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("Prince")
                                                .venue("First Avenue")
                                                .city("Minneapolis")
                                                .date(LocalDateTime.of(1983, 10, 20, 21, 0))
                                                .genre("Pop")
                                                .latitude(44.9762)
                                                .longitude(-93.2675)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("Beyoncé")
                                                .venue("SoFi Stadium")
                                                .city("Los Angeles")
                                                .date(LocalDateTime.of(2023, 5, 26, 20, 0))
                                                .genre("Pop")
                                                .latitude(33.9673)
                                                .longitude(-118.3393)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("Metallica")
                                                .venue("Berkeley Community Theater")
                                                .city("Berkeley")
                                                .date(LocalDateTime.of(1989, 9, 9, 20, 0))
                                                .genre("Heavy Metal")
                                                .latitude(37.8706)
                                                .longitude(-122.2727)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("Miles Davis")
                                                .venue("Newport Jazz Festival")
                                                .city("Newport")
                                                .date(LocalDateTime.of(1955, 7, 8, 20, 0))
                                                .genre("Jazz")
                                                .latitude(41.4901)
                                                .longitude(-71.3243)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("The Beatles")
                                                .venue("Ed Sullivan Theater")
                                                .city("New York")
                                                .date(LocalDateTime.of(1964, 2, 9, 20, 0))
                                                .genre("Rock")
                                                .latitude(40.7618)
                                                .longitude(-73.9776)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build(),

                                Concert.builder()
                                                .artist("Adele")
                                                .venue("Glastonbury Festival")
                                                .city("Somerset")
                                                .date(LocalDateTime.of(2022, 6, 26, 22, 0))
                                                .genre("Pop")
                                                .latitude(51.1504)
                                                .longitude(-2.1537)
                                                .createdAt(LocalDateTime.now())
                                                .updatedAt(LocalDateTime.now())
                                                .build());
        }
}
