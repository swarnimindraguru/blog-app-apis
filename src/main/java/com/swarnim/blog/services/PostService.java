package com.swarnim.blog.services;

import com.swarnim.blog.entities.Post;
import com.swarnim.blog.payloads.PostDto;
import com.swarnim.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    //Create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //Update
    PostDto updatePost(PostDto postDto, Integer postId);

    //Delete
    void deletePost(Integer postId);

    //Get ALl Post
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //Get Single Post
    PostDto getPostById(Integer postId);

    // Get All post by category
    List<PostDto> getPostByCategory(Integer categoryId)
    ;
    //Get All post by User
    List<PostDto> getAllPostByUser(Integer userId);

    //SearchPost
    List<PostDto> searchPost(String keyword);
}
