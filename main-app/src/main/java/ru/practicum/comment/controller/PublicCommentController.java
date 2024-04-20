package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class PublicCommentController {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final CommentService commentService;

    @GetMapping("/events/comments/{commentId}")
    public CommentDto get(@PathVariable Long commentId) {
        log.info("Get запрос на получение коммента - {}", commentId);
        return commentService.get(commentId);
    }

    @GetMapping("/events/{eventId}/comments")
    public List<CommentDto> getList(@PathVariable Long eventId,
                                    @RequestParam(required = false) List<Long> userIds,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeStart,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeEnd,
                                    @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                    @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Get запрос на получение списка комментов по event - {}", eventId);
        return commentService.getList(eventId, userIds, rangeStart, rangeEnd, from, size);
    }
}
