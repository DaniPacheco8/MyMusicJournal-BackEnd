package com.mymusic.journal.dto.response;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcertDTO {

    private Long id;
    private String artist;
    private String venue;
    private String city;
    private LocalDateTime date;
    private String genre;
    private Double latitude;
    private Double longitude;

}
