package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.error.ConflictException;
import ru.practicum.error.NotFoundException;
import ru.practicum.event.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto get(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(
                        "Category with id=" + catId + " was not found",
                        "The required object was not found."
                ));
        return CategoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getList(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("id"));
        Page<Category> categoryList = categoryRepository.findAll(pageable);
        return CategoryMapper.categoriesToCategoriesDto(categoryList.toList());
    }

    @Override
    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = CategoryMapper.categoryDtoToCategory(categoryDto);
        return CategoryMapper.categoryToCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(
                        "Category with id=" + catId + " was not found",
                        "The required object was not found."
                ));

        if (!eventRepository.findAllByCategoryId(catId).isEmpty()) {
            throw new ConflictException(
                    "Category with id=" + catId + " can not be deleted",
                    "there are some events that block deleting"
            );
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public CategoryDto update(Long catId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(
                        "Category with id=" + catId + " was not found",
                        "The required object was not found."
                ));

        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return CategoryMapper.categoryToCategoryDto(category);
    }
}
