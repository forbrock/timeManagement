package com.spring.project.dto.mapper;

import com.spring.project.dto.CategoryDto;
import com.spring.project.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryDto> mapCategoryToCategoryDto(List<Category> category);
}
