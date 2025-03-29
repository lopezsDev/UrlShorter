package com.api.urlshorter.config;

import com.api.urlshorter.repository.ShorterUrlRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UrlMetrics {

    private final ShorterUrlRepository urlRepo;

    public UrlMetrics(ShorterUrlRepository urlRepo, MeterRegistry meterRegistry) {
        this.urlRepo = urlRepo;

        meterRegistry.gauge("total_urls_acortadas", urlRepo.count());
        meterRegistry.gauge("total_urls_expiradas", urlRepo, repo -> (double) countExpiredUrls());

    }

    private long countExpiredUrls() {
        return urlRepo.findAll().stream()
                .filter(url -> url.getExpirationDate() != null && url.getExpirationDate().isBefore(LocalDateTime.now()))
                .count();
    }
}
