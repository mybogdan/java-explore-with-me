package ru.practicum.service;

import ru.practicum.dto.StatDto;
import ru.practicum.dto.ViewStats;

import java.util.Date;
import java.util.List;

public interface StatService {
    void create(StatDto statDto);

    List<ViewStats> get(Date start, Date end, List<String> uris, Boolean unique);

    Long getCount(String uri);
}
