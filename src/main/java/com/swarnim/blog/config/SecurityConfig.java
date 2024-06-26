package com.swarnim.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import com.swarnim.blog.security.CustomUserDetailService;
import com.swarnim.blog.security.JwtAuthenticationEntryPoint;
import com.swarnim.blog.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc //Used for Swagger
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

    private CustomUserDetailService customUserDetailService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(CustomUserDetailService customUserDetailService,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailService = customUserDetailService;
        this.jwtAuthenticationEntryPoint=jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter=jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf( csrf -> csrf.disable())
                .cors( cors-> cors.disable())
                .authorizeHttpRequests( auth-> auth.requestMatchers(AppConstants.PUBLIC_URL).permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling( ex-> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        /* http .csrf( (csrf)-> csrf.disable() )
                .authorizeHttpRequests( auth -> auth.requestMatchers(PUBLIC_URL).permitAll()
                        .anyRequest().authenticated() )
                .exceptionHandling( (exception)-> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement( (session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                .httpBasic(Customizer.withDefaults()) ;
         */
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*  was used with WebSecurityConfigurerAdapter
    protected void Configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

     */
    @Bean // used to implement basic auth from db
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


    @Bean  // method used to authenticate user and pass
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}