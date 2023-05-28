package com.example.cardapio.repository;

import com.example.cardapio.modelo.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<Food, String> {
}
