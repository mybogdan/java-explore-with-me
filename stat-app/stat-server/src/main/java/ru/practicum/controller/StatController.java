package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatController {

    private final StatService statService;


    @PostMapping("/hit")
    public void create(@RequestBody @Valid StatDto statDto) {
        log.info("Пришел /POST запрос");
        statService.create(statDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> get(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                               @RequestParam(required = false) List<String> uris,
                               @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Пришел /GET запрос");
        List<ViewStats> result = statService.get(start, end, uris, unique);
        log.info("Ответ отправлен");
        return result;

    }
}
