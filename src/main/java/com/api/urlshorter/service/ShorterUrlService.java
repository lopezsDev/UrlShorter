package com.api.urlshorter.service;

import com.api.urlshorter.model.ShorterUrlModel;
import com.api.urlshorter.repository.ShorterUrlRepository;
import com.api.urlshorter.utils.InputSanitizer;
import com.api.urlshorter.utils.SafeBrowsingChecker;
import com.api.urlshorter.utils.ValidationUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ShorterUrlService {

    @Autowired
    private ShorterUrlRepository urlRepo;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    public String shortenUrl(String originalUrl, Long expirationTimeInHours) {
        validateAndSanitize(originalUrl);

        String shortCode = generateShortCode();
        LocalDateTime expirationDate = expirationTimeInHours != null ?
                LocalDateTime.now().plusHours(expirationTimeInHours)  : null;

        ShorterUrlModel newUrl = new ShorterUrlModel();
        newUrl.setOriginalUrl(originalUrl);
        newUrl.setShortCode(shortCode);
        newUrl.setExpirationDate(expirationDate);
        urlRepo.save(newUrl);

        redisTemplate.opsForValue().set(shortCode, originalUrl,
                expirationTimeInHours != null ? Duration.ofHours(expirationTimeInHours) : Duration.ofHours(24));

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {

        String cacheUrl = (String) redisTemplate.opsForValue().get(shortCode);
        if (cacheUrl != null) {
            return cacheUrl;
        }

        return urlRepo.findByShortCode(shortCode)
                .map(url -> {
                    redisTemplate.opsForValue().set(shortCode, url.getOriginalUrl(), Duration.ofHours(24));
                    return url.getOriginalUrl();
                })
                .orElseThrow(() -> new RuntimeException("URL no encontrada o expirada: " + shortCode));
    }

    public void validateAndSanitize(String url) {
        String sanitized = InputSanitizer.sanitize(url);

        if (!ValidationUtils.isValidUrl(sanitized)) {
            throw new IllegalArgumentException("URL no válida");
        }

        if (!SafeBrowsingChecker.isSafeUrl(sanitized)) {
            throw new IllegalArgumentException("La URL apunta a contenido malicioso");
        }
    }

    private String generateShortCode() {
        return  RandomStringUtils.randomAlphanumeric(6);
    }


}
