package com.api.urlshorter.repository;

import com.api.urlshorter.model.ShorterUrlModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShorterUrlRepository extends MongoRepository<ShorterUrlModel, String>{
    Optional<ShorterUrlModel> findByShortCode(String shortCode);

}

