package com.spring.project.service;

import com.spring.project.dto.CategoryDto;
import com.spring.project.dto.mapper.CategoryMapper;
import com.spring.project.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryMapper mapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public List<CategoryDto> getAllCategories() {
        return mapper.mapCategoryToCategoryDto(categoryRepository.findAll());
    }
}
