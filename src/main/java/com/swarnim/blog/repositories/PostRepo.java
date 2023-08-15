package com.swarnim.blog.repositories;

import com.swarnim.blog.entities.Category;
import com.swarnim.blog.entities.Post;
import com.swarnim.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    //We can also use @Query to directly fetch data from DB like :
//    @Query("select p from Post p where p.title like :key")
//    List<Post> searchByTitle(@Param("key") String title);
    List<Post> findByTitleContaining(String title);
}
