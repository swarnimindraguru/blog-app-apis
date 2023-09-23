package com.swarnim.blog.config;

public class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "10";
    public static final String SORT_BY = "postId";
    public static final String SORT_DIR = "asc";
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // in milli sec
    public static final String SECRET = "jwtTokenKeydfsfghjkiasdfghjkjwtTokenKeydfsfghjkiasdfghjklkjhgfdsxcvbnmkiuytresxcnuytrd" +
            "lkjhgfdsxcvbnmkiuytresxcnuytrd";
//    long JWT_TOKEN_VALIDITY =5 * 60 * 60; // in milli sec
//    String SECRET ="jwtTokenKeydfsfghjkiasdfghjkjwtTokenKeydfsfghjkiasdfghjklkjhgfdsxcvbnmkiuytresxcnuytrd" +
//            "lkjhgfdsxcvbnmkiuytresxcnuytrd";

    public static final Integer ADMIN_USER=101;
    public static final Integer NORMAL_USER=102;
    static String [] PUBLIC_URL={
            "/api-docs",
            "/v2/api-docs",
            "/home/auth/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/api/users/createUser"
    };



}
