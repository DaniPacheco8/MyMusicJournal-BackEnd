package com.mymusic.journal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertMapDTO {
    private Long id;
    private String artist;
    private String city;
    private Double latitude;
    private Double longitude;
}
