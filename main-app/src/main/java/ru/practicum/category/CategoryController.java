package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto save(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("Пришел /POST ADMIN запрос на добавление новой категории - {}", categoryDto.getName());
        return categoryService.save(categoryDto);
    }

    @DeleteMapping("/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long catId) {
        log.info("Пришел /DELETE ADMIN запрос на удаление категории с id {}", catId);
        categoryService.delete(catId);
    }

    @PatchMapping("/admin/categories/{catId}")
    public CategoryDto update(@PathVariable Long catId,
                              @RequestBody @Valid CategoryDto categoryDto) {
        log.info("Пришел /PATCH ADMIN запрос на обновление категории с id {}", catId);
        return categoryService.update(catId, categoryDto);
    }

    @GetMapping("/categories")
    public List<CategoryDto> getList(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                     @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Пришел /GET запрос на получение всех категорий");
        return categoryService.getList(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto get(@PathVariable Long catId) {
        log.info("Пришел /GET запрос на получение информации о категории с id {}", catId);
        return categoryService.get(catId);
    }

}
