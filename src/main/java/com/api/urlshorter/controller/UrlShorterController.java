package com.api.urlshorter.controller;

import com.api.urlshorter.dto.UrlRequestDTO;
import com.api.urlshorter.service.ShorterUrlService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class UrlShorterController {

    @Autowired
    private ShorterUrlService urlService;

    @Operation(summary = "Método para recibir URL y acortarla")
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlRequestDTO urlRequestDTO) {
        String shortCode = urlService.shortenUrl(urlRequestDTO.originalUrl(), urlRequestDTO.expirationTimeInHours());
        return ResponseEntity.ok(shortCode);
    }

    @Operation(summary = "Método para recuperar la URL original")
    @GetMapping("/getOriginal/{shortCode}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.ok(originalUrl);
    }

}
