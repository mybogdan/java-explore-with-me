package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> get(@PathVariable Long userId) throws ParseException {
        log.info("Пришел /GET PRIVATE запрос на получение информации о заявках пользователя с id {} на участие в чужих событиях", userId);
        return requestService.get(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/requests")
    public ParticipationRequestDto save(@PathVariable Long userId,
                                        @RequestParam Long eventId) throws ParseException {
        log.info("Пришел /POST PRIVATE запрос на участие в событии с id {}, от текущего пользователя - {}", eventId, userId);
        return requestService.save(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable Long userId,
                                          @PathVariable Long requestId) throws ParseException {
        log.info("Пришел /PATCH PRIVATE запрос на отмену собственного запроса на участие в событие с id {}, от пользователя - {}", requestId, userId);
        return requestService.cancel(userId, requestId);
    }
}
