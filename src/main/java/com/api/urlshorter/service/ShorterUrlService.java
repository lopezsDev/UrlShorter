package com.api.urlshorter.service;

import com.api.urlshorter.model.ShorterUrlModel;
import com.api.urlshorter.repository.ShorterUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ShorterUrlService {

    @Autowired
    private ShorterUrlRepository urlRepo;

    public String shortenUrl(String originalUrl, Long expirationTimeInHours) {

        String shortCode = generateShortCode();
        LocalDateTime expirationDate = expirationTimeInHours != null ?
                LocalDateTime.now().plusHours(expirationTimeInHours)  : null;

        ShorterUrlModel newUrl = new ShorterUrlModel();
        newUrl.setOriginalUrl(originalUrl);
        newUrl.setShortCode(shortCode);
        newUrl.setExpirationDate(expirationDate);

        urlRepo.save(newUrl);
        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        return urlRepo.findByShortCode(shortCode)
                .filter(url -> url.getExpirationDate() == null || url.getExpirationDate().isAfter(LocalDateTime.now()))
                .map(ShorterUrlModel::getOriginalUrl)
                .orElseThrow(()-> new RuntimeException("URL no encontrada o expirada: " + shortCode));

    }

    public void cleanedUlr(Long expirationTimeInHours) {

    }

    private String generateShortCode() {
        return Integer.toHexString(new Random().nextInt(1000000));
    }
}
