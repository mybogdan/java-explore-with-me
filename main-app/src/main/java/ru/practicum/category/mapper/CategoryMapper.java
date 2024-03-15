package ru.practicum.category.mapper;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryDto categoryToCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public static List<CategoryDto> categoriesToCategoriesDto(List<Category> categories) {
        List<CategoryDto> categoriesDto = new ArrayList<>();

        for (Category category : categories) {
            categoriesDto.add(categoryToCategoryDto(category));
        }
        return categoriesDto;
    }

    public static Category categoryDtoToCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName()
        );
    }
}
