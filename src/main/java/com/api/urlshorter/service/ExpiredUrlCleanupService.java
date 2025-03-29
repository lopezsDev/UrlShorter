package com.api.urlshorter.service;

import com.api.urlshorter.repository.ShorterUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ExpiredUrlCleanupService {

    @Autowired
    private ShorterUrlRepository urlRepo;

    @Scheduled(fixedRate = 3600000) // Ejecuta cada hora
    public void cleanExpiredUrls() {
        urlRepo.findAll().stream()
                .filter(url -> url.getExpirationDate() != null && url.getExpirationDate().isBefore(LocalDateTime.now()))
                .forEach(urlRepo::delete);
    }
}
