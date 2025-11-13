package com.mymusic.journal.service;

import com.mymusic.journal.dto.request.UserLoginRequestDTO;
import com.mymusic.journal.dto.request.UserRegisterRequestDTO;
import com.mymusic.journal.dto.response.AuthResponseDTO;
import com.mymusic.journal.dto.response.UserResponseDTO;
import com.mymusic.journal.entity.User;
import com.mymusic.journal.repository.UserRepository;
import com.mymusic.journal.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final JwtTokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponseDTO register(UserRegisterRequestDTO request) {
        if (!request.getPassword().equals(request.getPasswordConfirmation())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        String token = tokenProvider.generateToken(savedUser.getId(), savedUser.getEmail());

        UserResponseDTO userDTO = UserResponseDTO.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();

        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(86400000L)
                .user(userDTO)
                .build();
    }

    public AuthResponseDTO login(UserLoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = tokenProvider.generateToken(user.getId(), user.getEmail());

            UserResponseDTO userDTO = UserResponseDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build();

            return AuthResponseDTO.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .expiresIn(86400000L)
                    .user(userDTO)
                    .build();

        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for email: {}", request.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        } catch (Exception e) {
            log.error("Error during login", e);
            throw new RuntimeException("Error during authentication", e);
        }
    }
}