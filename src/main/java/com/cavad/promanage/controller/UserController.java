package com.cavad.promanage.controller;

import com.cavad.promanage.baseresponse.ResponseModel;
import com.cavad.promanage.baseresponse.ResponseModelService;
import com.cavad.promanage.config.JwtResponse;
import com.cavad.promanage.config.JwtUtil;
import com.cavad.promanage.dto.LoginRequest;
import com.cavad.promanage.dto.RegistrationResponse;
import com.cavad.promanage.dto.UpdateUserDto;
import com.cavad.promanage.dto.UserDto;
import com.cavad.promanage.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    @GetMapping("/protected-data")
    public String getProtectedData() {
        return "Bu veri, geçerli bir JWT token'ı ile erişilebilen bir alandır!";
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody @Valid  UserDto  userDto) {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                userService.createUser(userDto),
                "Successfully",
                HttpStatus.OK);



    }
    @GetMapping("/getTasks")
    public ResponseEntity<Object> getTasks() {

        return ResponseModelService
                .responseBuilder(LocalDateTime.now(),
                        userService.getUserTasks(),
                        "Successfully",
                        HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateProfile(@RequestBody UpdateUserDto request) {

        return ResponseModelService
                .responseBuilder(LocalDateTime.now(),
                        userService.updateUser(request),
                        "Successfully",
                        HttpStatus.OK);
    }

//    @GetMapping("/activate/{email}")
//    public ResponseEntity<?> activateProfile(@PathVariable String email) {
//
//        return ResponseModelService
//                .responseBuilderaList(LocalDateTime.now(),
//                        userService.activateProfile(email),
//                        "Successfully",
//                        HttpStatus.OK);
//    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(),
                            loginRequest.password()));

        } catch (UsernameNotFoundException usernameNotFoundException) {
            usernameNotFoundException.printStackTrace();
            throw new Exception("Bad Credential");
        }

        UserDetails userDetails = this.userService.loadUserByUsername(loginRequest.email());

        String token = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
