package com.swarnim.blog.controllers;

import com.swarnim.blog.entities.User;
import com.swarnim.blog.exceptions.ApiException;
import com.swarnim.blog.payloads.JwtAuthRequest;
import com.swarnim.blog.payloads.JwtAuthResponse;
import com.swarnim.blog.payloads.UserDto;
import com.swarnim.blog.security.JwtHelper;
import com.swarnim.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping("/home/auth/")
public class AuthController {

    private JwtHelper jwtTokenHelper;
    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager; // to authenticate username/ password
    private UserService userService;

    @Autowired
    public AuthController(JwtHelper jwtTokenHelper, UserDetailsService userDetailsService,
                          AuthenticationManager authenticationManager, UserService userService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService=userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
    ) throws Exception {

        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
        //System.out.println("------------------------>USER IS AUTHENTICATED ");

        String token=jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response= new JwtAuthResponse();
        response.setToken(token);
        response.setCreatedAt(new Date());

        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

    }
    private void authenticate(String email, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(email,password);
        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw  new ApiException("Invalid Credentials");
        }
    }

//    register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registerUser=userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);

    }
}
