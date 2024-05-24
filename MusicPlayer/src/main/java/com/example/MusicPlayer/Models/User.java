package com.example.MusicPlayer.Models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotBlank
    @Min(value = 5, message = "Username must be at least 5 characters")
    private String username;
    @NotBlank
    @Min(value = 5, message = "Password must be at least 5 characters")
    private String password;
    @NotBlank
    private String email;
}
