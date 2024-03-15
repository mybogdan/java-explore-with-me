package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.model.Compilation;

import java.text.ParseException;
import java.util.List;

public interface CompilationService {

    CompilationDto get(Long compId) throws ParseException;

    List<CompilationDto> getList(Boolean pinned, int from, int size);

    CompilationDto save(NewCompilationDto newCompilationDto) throws ParseException;

    void delete(Long compId);

    CompilationDto patch(Long compId, UpdateCompilationRequest updateCompilationRequest) throws ParseException;
}
