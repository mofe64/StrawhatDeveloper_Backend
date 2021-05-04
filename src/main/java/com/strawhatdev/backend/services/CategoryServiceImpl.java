package com.strawhatdev.backend.services;

import com.strawhatdev.backend.exceptions.CategoryException;
import com.strawhatdev.backend.models.Category;
import com.strawhatdev.backend.respository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryByName(String name) throws CategoryException {
        return getCategory(name)
                .orElseThrow(() -> new CategoryException("No category found with that name"));
    }

    private Optional<Category> getCategory(String name) {
        return categoryRepository.findCategoryByName(name);
    }
}
