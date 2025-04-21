package com.api.urlshorter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "DTO para acortar una URL")
public record UrlRequestDTO(
        @Schema(description = "URL original", example = "https://openai.com")
        @NotBlank @Size(max = 2048) String originalUrl,

        @Schema(description = "Tiempo de expiración en horas", example = "24")
        @NotNull @Future Long expirationTimeInHours
) {
}
