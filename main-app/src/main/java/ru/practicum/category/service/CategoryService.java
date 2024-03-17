package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto get(Long catId);

    List<CategoryDto> getList(int from, int size);

    CategoryDto save(CategoryDto categoryDto);

    void delete(Long catId);

    CategoryDto update(Long catId, CategoryDto categoryDto);
}
