package com.api.urlshorter.dto;

import java.time.LocalDateTime;

public record UrlRequestDTO(
        String originalUrl,
        Long expirationTimeInHours
) {
}
