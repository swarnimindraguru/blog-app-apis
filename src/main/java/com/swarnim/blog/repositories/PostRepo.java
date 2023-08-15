package com.swarnim.blog.repositories;

import com.swarnim.blog.entities.Category;
import com.swarnim.blog.entities.Post;
import com.swarnim.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}
