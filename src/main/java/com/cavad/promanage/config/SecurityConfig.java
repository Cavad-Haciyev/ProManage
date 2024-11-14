package com.cavad.promanage.config;

import com.cavad.promanage.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtAuthFilter;
    private final UserService userService;



    public SecurityConfig(JwtFilter jwtAuthFilter, UserService userService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x ->
                        x.requestMatchers("/api/users/register","/api/users/login","/api/users/activate/{email}","/api/users/protected-data").permitAll()
                                .requestMatchers("/api/users/updateUser").authenticated()
                                .requestMatchers("/api/users/getTasks").authenticated()
                )
                .authorizeHttpRequests(x ->
                        x.requestMatchers("/api/project").authenticated()
                                .requestMatchers("/api/project/getAllProject").authenticated()
                                .requestMatchers("/api/project/{name}").authenticated()
                                .requestMatchers("/api/project/delete/{id}").hasRole("ADMIN")
                                .requestMatchers("/api/project/createProject").authenticated()

                )

                .authorizeHttpRequests(x ->
                        x.requestMatchers("/api/task").authenticated()
                                .requestMatchers("/api/task/createTask").hasRole("ADMIN")
                                .requestMatchers("/api/task/completed/{id}").hasRole("ADMIN")
                                .requestMatchers("/api/task/ongoingTasks").hasRole("ADMIN")
                                .requestMatchers("/api/task/taskById").hasRole("ADMIN")
                                .requestMatchers("/api/task/updateTask/{id}").hasRole("ADMIN")
                                .requestMatchers("/api/task/deleteTask/{id}").hasRole("ADMIN")




                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder instance = NoOpPasswordEncoder.getInstance();
        return instance;
    }
}

