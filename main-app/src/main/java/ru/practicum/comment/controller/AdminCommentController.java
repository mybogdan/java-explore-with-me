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
public class AdminCommentController {

    private final CommentService commentService;

    @PatchMapping("/admin/events/comments/{commentId}")
    public CommentDto patchByAdmin(@PathVariable Long commentId,
                                   @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Patch запрос на обновление комментария - {}", newCommentDto);
        return commentService.patchByAdmin(newCommentDto, commentId);
    }

    @DeleteMapping("/admin/events/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByAdmin(@PathVariable Long commentId) {
        log.info("Delete запрос на удаление коммента - {}", commentId);
        commentService.deleteByAdmin(commentId);
    }
}
