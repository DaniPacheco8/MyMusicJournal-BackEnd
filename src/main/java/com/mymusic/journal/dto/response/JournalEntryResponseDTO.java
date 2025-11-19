package com.mymusic.journal.dto.response;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntryResponseDTO {

    private Long id;
    private Long concertId;
    private String concertTitle;
    private String personalNotes;
    private Integer rating;
    private String backgroundImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Concert details for filtering and display
    private LocalDateTime date;
    private String city;
    private String genre;

}
