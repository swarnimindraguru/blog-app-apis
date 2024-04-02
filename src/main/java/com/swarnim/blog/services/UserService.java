package com.swarnim.blog.services;

import com.swarnim.blog.entities.User;
import com.swarnim.blog.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
    UserDto registerNewUser(UserDto user);
    void updateRole(Integer userID, Integer roleID);
}
