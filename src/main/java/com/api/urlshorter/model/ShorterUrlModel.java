package com.api.urlshorter.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@RedisHash
@Document(collection = "urls")
public class ShorterUrlModel {

    @Id
    private String id;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime expirationDate;
}
