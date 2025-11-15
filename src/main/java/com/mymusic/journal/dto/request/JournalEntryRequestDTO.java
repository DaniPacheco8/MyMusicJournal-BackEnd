package com.mymusic.journal.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntryRequestDTO {

    @NotNull(message = "Concert ID cannot be null")
    @Positive(message = "Concert ID must be a positive number")
    private Long concertId;

    @NotBlank(message = "Personal notes cannot be blank")
    @Size(min = 10, max = 5000, message = "Personal notes must be between 10 and 5000 characters")
    private String personalNotes;

}
