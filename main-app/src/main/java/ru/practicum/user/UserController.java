package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/admin/users")
    public List<UserDto> getList(@RequestParam(required = false) List<Long> ids,
                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                 @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Пришел /GET ADMIN запрос на получение списка пользователей");
        return userService.getList(ids, from, size);
    }

    @PostMapping("/admin/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto save(@RequestBody @Valid UserDto userDto) {
        log.info("Пришел /POST запрос на создание пользователя");
        return userService.save(userDto);
    }

    @DeleteMapping("/admin/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Пришел /DELETE запрос на удаление пользователя с id {}", id);
        userService.delete(id);
    }
}
