package com.swarnim.blog.controllers;

import com.swarnim.blog.entities.User;
import com.swarnim.blog.payloads.ApiResponse;
import com.swarnim.blog.payloads.UserDto;
import com.swarnim.blog.services.UserService;
import com.swarnim.blog.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    /* Not needed as we have developed RegisterUser API */
    //POST
//    @PostMapping ("/createUser")
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
//        UserDto createdUserDto = this.userService.createUser(userDto);
//        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
//    }
    //PUT
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId")Integer uid){
        UserDto updatedUser = this.userService.updateUser(userDto, uid);
        return ResponseEntity.ok(updatedUser);
    }

    //DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{userId}")
//    public ResponseEntity<ApiResponse> deleteUser (@PathVariable Integer userId){
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
    }
    //GET ALL
    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    //GET
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    // UPDATE ROLE
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{userID}/role/{roleID}")
    public ResponseEntity<ApiResponse> updateRole(@PathVariable Integer userID,
                                                     @PathVariable Integer roleID){
        userService.updateRole(userID,roleID);
        ApiResponse responseDto= new ApiResponse("Role Has been Updated..!", true);

        return ResponseEntity.ok(responseDto);
    }

    //POST - USER Bulk Register
        @PostMapping ("/userBulkRegister")
    public ResponseEntity<ApiResponse> userBulkRegister(@RequestParam("file") MultipartFile file) throws IOException {
        if(userServiceImpl.checkExcelFormate(file)){
            userService.registerUsersInBulk(file);
            ApiResponse response= new ApiResponse("Users are successfully registered", true);
            return ResponseEntity.ok(response);
        }
        ApiResponse response= new ApiResponse("Please Upload a .xlsx file", false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
