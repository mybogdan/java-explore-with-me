package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatClient;
import ru.practicum.dto.StatDto;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.EventState;
import ru.practicum.event.service.EventService;
import ru.practicum.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventController {

    private final EventService eventService;
    private final StatClient statClient;

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getList(@PathVariable long userId,
                                       @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                       @RequestParam(defaultValue = "10") @Positive int size) throws ParseException {
        log.info("Пришел /GET PRIVATE запрос на получение событий, добавленных текущим пользователем с id {}", userId);

        return eventService.getList(userId, from, size);
    }

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto save(@PathVariable long userId,
                             @Valid @RequestBody NewEventDto newEventDto) throws ParseException {
        log.info("Пришел /POST PRIVATE запрос от пользователя с id {} на добавление нового события", userId);
        return eventService.save(userId, newEventDto);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto get(@PathVariable long userId,
                            @PathVariable long eventId) throws ParseException {
        log.info("Пришел /GET PRIVATE запрос на получение полной информации о событие с id {}, добавленном текущим пользователем с id {}", eventId, userId);
        return eventService.get(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto patch(@PathVariable long userId,
                              @PathVariable long eventId,
                              @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) throws ParseException {
        log.info("Пришел /PATCH PRIVATE запрос на обновление собития с id {}, добавленным текущим пользователем с id {}", eventId, userId);
        return eventService.patch(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId,
                                                     @PathVariable Long eventId) throws ParseException {
        log.info("Пришел /GET PRIVATE запрос на получение информации о чужих запросах на участие в событии с id {} текущего пользователя с id {}", eventId, userId);
        return eventService.getRequests(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult patchRequest(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @RequestBody EventRequestStatusUpdateRequest requestUpdate) throws ParseException {
        log.info("Пришел /PATCH PRIVATE запрос на обновление заявок на участие с id {}, добавленным текущим пользователем с id {}", eventId, userId);
        return eventService.patchRequest(userId, eventId, requestUpdate);
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> getListByAdmin(@RequestParam(required = false) List<Long> users,
                                             @RequestParam(required = false) List<EventState> states,
                                             @RequestParam(required = false) List<Long> categories,
                                             @RequestParam(required = false) String rangeStart,
                                             @RequestParam(required = false) String rangeEnd,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(defaultValue = "10") @Positive int size) throws ParseException {
        log.info("Пришел /GET ADMIN запрос на получение событий");
        return eventService.getListByAdmin(users, states, categories, rangeEnd, rangeStart, from, size);
    }


    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto patchByAdmin(@PathVariable Long eventId,
                                     @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest) throws ParseException {
        log.info("Пришел /PATCH ADMIN запрос на обновление события с id {}", eventId);
        return eventService.patchByAdmin(eventId, updateEventAdminRequest);
    }

    @GetMapping("/events")
    public List<EventShortDto> getListByPublic(@RequestParam(required = false) String text,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @RequestParam(required = false) String rangeStart,
                                               @RequestParam(required = false) String rangeEnd,
                                               @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                               @RequestParam(required = false) String sort,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size,
                                               HttpServletRequest request) throws ParseException {

        log.info("Пришел /GET запрос на получение списка событий");

        statClient.postStats(new StatDto("ewm-main-service", request.getRequestURI(), request.getRemoteAddr()));
        return eventService.getListByPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getByPublic(@PathVariable Long id,
                                    HttpServletRequest request) throws ParseException {
        log.info("Пришел /GET запрос на получение события с id {}", id);
        statClient.postStats(new StatDto("ewm-main-service", request.getRequestURI(), request.getRemoteAddr()));
        return eventService.getByPublic(id);
    }

}
