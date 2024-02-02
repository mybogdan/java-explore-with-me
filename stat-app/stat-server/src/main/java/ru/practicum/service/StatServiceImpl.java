package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.mapper.StatMapper;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    @Override
    public void create(StatDto statDto) {
        statRepository.save(StatMapper.statDtoToStat(statDto));
    }

    @Override
    public List<ViewStats> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (unique) {
            return statRepository.searchUnique(start, end, uris == null ? new ArrayList<String>() : uris);
        } else {
            return statRepository.search(start, end, uris == null ? new ArrayList<String>() : uris);
        }
    }
}
