package com.swarnim.blog.controllers;

import com.swarnim.blog.entities.Comment;
import com.swarnim.blog.payloads.ApiResponse;
import com.swarnim.blog.payloads.CommentDto;
import com.swarnim.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // CREATE COMMENT
    @PostMapping("post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {
       CommentDto createComment = this.commentService.createComment(commentDto, postId);
       return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
    }

    //DELETE COMMENT

    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully!!", true), HttpStatus.OK);
    }

}
