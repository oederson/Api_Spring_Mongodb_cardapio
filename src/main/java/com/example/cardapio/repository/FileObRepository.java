package com.example.cardapio.repository;

import com.example.cardapio.modelo.FileOb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileObRepository extends MongoRepository<FileOb, String> {

}

