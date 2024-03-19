package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping("/users/{userId}/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto save(@PathVariable Long userId,
                           @PathVariable Long eventId,
                           @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Post запрос на добавление коммента от user - {}, по event - {}: {}", userId, eventId, newCommentDto);
        return commentService.save(newCommentDto, userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/comments/{commentId}")
    public CommentDto patch(@PathVariable Long userId,
                            @PathVariable Long commentId,
                            @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Patch запрос на обновление коммента - {} от user - {}: {}", commentId, userId, newCommentDto);
        return commentService.patch(newCommentDto, userId, commentId);
    }

    @DeleteMapping("/users/{userId}/events/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId,
                       @PathVariable Long commentId) {
        log.info("Delete запрос на удаление коммента - {} от user - {}", commentId, userId);
        commentService.delete(userId, commentId);
    }
}
