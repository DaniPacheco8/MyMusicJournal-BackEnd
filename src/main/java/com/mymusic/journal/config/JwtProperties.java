package com.mymusic.journal.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {

    private String secret;

    private Long expirationMs;
}
