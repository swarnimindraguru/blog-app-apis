package com.swarnim.blog.services;

import com.swarnim.blog.entities.Post;
import com.swarnim.blog.payloads.PostDto;

import java.util.List;

public interface PostService {
    //Create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //Update
    PostDto updatePost(PostDto postDto, Integer postId);

    //Delete
    void deletePost(Integer postId);

    //Get ALl Post
    List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);

    //Get Single Post
    PostDto getPostById(Integer postId);

    // Get All post by category
    List<PostDto> getPostByCategory(Integer categoryId)
    ;
    //Get All post by User
    List<PostDto> getAllPostByUser(Integer userId);

    //SearchPost
    List<Post> searchPost(String keyword);
}
