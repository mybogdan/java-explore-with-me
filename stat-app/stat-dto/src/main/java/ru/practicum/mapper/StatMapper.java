package ru.practicum.mapper;

import ru.practicum.dto.StatDto;
import ru.practicum.model.Stat;

public class StatMapper {

    public static Stat statDtoToStat(StatDto statDto) {
        return Stat.builder()
                .app(statDto.getApp())
                .uri(statDto.getUri())
                .ip(statDto.getIp())
                .timestamp(statDto.getTimestamp())
                .build();
    }

}
