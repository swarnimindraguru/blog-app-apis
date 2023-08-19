package com.swarnim.blog.services;

import com.swarnim.blog.entities.Comment;
import com.swarnim.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);
}
