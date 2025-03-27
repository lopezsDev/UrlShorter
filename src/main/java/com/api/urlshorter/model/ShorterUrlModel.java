package com.api.urlshorter.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collation = "urls")
public class ShorterUrlModel {

    @Id
    private String id;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime expirationDate;
}
