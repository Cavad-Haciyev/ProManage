package com.cavad.promanage.service;

import com.cavad.promanage.dto.RegistrationResponse;
import com.cavad.promanage.dto.UpdateUserDto;
import com.cavad.promanage.dto.UserDto;
import com.cavad.promanage.dto.UserTaskResponse;
import com.cavad.promanage.dto.convertor.TaskConvertor;
import com.cavad.promanage.dto.convertor.UserConverter;
import com.cavad.promanage.exception.EmailAlreadyExistException;
import com.cavad.promanage.exception.UserNotFoundException;
import com.cavad.promanage.model.Role;
import com.cavad.promanage.model.User;
import com.cavad.promanage.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final TaskConvertor taskConvertor;
    private final RoleService roleService;
    private final EmailService service;

    public UserService(UserRepository userRepository, UserConverter userConverter, TaskConvertor taskConvertor, RoleService roleService, EmailService service) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.taskConvertor = taskConvertor;
        this.roleService = roleService;

        this.service = service;
    }


    //GetUserTasks
    public List<UserTaskResponse> getUserTasks() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User byEmail = userRepository.findByEmail(userEmail);
        UserTaskResponse build = UserTaskResponse.builder()
                .userTasks(byEmail.getTasks()
                        .stream()
                        .map(taskConvertor::convertToTaskUserResponse)
                        .collect(Collectors.toList()))
                .build();
        return Collections.singletonList(build);
    }



    //UpdateUser
    public String updateUser(UpdateUserDto userDto) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User byEmail = userRepository.findByEmail(name);
        byEmail.setUserName(userDto.userName());
        userRepository.save(byEmail);
        return "Successfully Updated";


    }

    //Registration
    public String createUser(UserDto userDto) {

        double ttt = Math.random() * 100000000;
        String rand = Double.toString(ttt).substring(2, 8);
        Role roleById = roleService.getRoleById(userDto.roleId());

        User userExist = userRepository.findByEmail(userDto.email());
        if (userExist != null) {
            throw new EmailAlreadyExistException();
        }
        User user = new User();
        user.setUserName("User_" + rand);
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        user.setActive(true);
        user.setRoles(Collections.singletonList(roleById));
        userRepository.save(user);
//        String link = "http://localhost:8081/api/users/activate/" + user.getEmail();
//        service.sendSimpleEmail(user.getEmail(),
//                link,
//                "Activate Link");
//
//        return link;
        return "Succesfully Registration";

    }

    //Activate Profile
//    public RegistrationResponse activateProfile(String email) {
//        User user = userRepository
//                .findByEmail(email);
//        user.setActive(true);
//        userRepository.save(user);
//        return userConverter.convertToRegistrationResponse(user);
//    }

    //Login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getActive()) {
            throw new UserNotFoundException();
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }


    protected User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

