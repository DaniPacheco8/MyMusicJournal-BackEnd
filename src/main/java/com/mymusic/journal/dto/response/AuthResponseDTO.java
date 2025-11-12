package com.mymusic.journal.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private String token;
    private String tokenType;
    private Long expiresIn;
    private UserResponseDTO user;

}
