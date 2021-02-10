package com.spring.project.service;

import com.spring.project.dto.CategoryDto;
import com.spring.project.dto.RegistrationDto;
import com.spring.project.dto.mapper.CategoryMapper;
import com.spring.project.exceptions.CategoryAlreadyExistException;
import com.spring.project.exceptions.UserAlreadyExistException;
import com.spring.project.model.Category;
import com.spring.project.model.User;
import com.spring.project.model.enums.Role;
import com.spring.project.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional
    public Category create(CategoryDto categoryDto)
            throws CategoryAlreadyExistException {

        Category category = mapper.dtoToCategory(categoryDto);
        return categoryRepository.save(category);
    }

    public List<CategoryDto> getAll() {
        return mapper.mapCategoryToCategoryDto(categoryRepository.findAll());
    }

    @Transactional
    public Category update(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(() ->
                new NoSuchElementException("No such category with id: " + categoryDto.getId()));
        String oldName = category.getName();
        category.setName(categoryDto.getName());
        category.setLastModified(LocalDateTime.now());
        log.info("Category {} was modified to {}", oldName, categoryDto.getName());
        return categoryRepository.save(category);
    }

    public Long deleteById(long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            log.info("Deleted category with id: " + id);
        }
        return id;
    }

    public Category getById(long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No such category was found, id: " + id));
    }
}
