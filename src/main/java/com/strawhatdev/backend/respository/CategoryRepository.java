package com.strawhatdev.backend.respository;

import com.strawhatdev.backend.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findCategoryByName(String name);
}
