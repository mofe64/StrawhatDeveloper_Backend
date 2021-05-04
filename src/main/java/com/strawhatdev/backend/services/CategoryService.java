package com.strawhatdev.backend.services;

import com.strawhatdev.backend.dtos.CategoryDto;
import com.strawhatdev.backend.exceptions.CategoryException;
import com.strawhatdev.backend.models.Category;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CategoryService {
    Category getCategoryByName(String name) throws CategoryException;
}
