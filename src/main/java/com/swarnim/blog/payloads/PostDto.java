package com.swarnim.blog.payloads;


import com.swarnim.blog.entities.Category;
import com.swarnim.blog.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.crypto.Data;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private  Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Data addedDate;

    private CategoryDto category;
    private UserDto user;


}
