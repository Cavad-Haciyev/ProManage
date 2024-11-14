package com.cavad.promanage.dto.convertor;

import com.cavad.promanage.dto.RegistrationResponse;
import com.cavad.promanage.dto.UpdateUserDto;
import com.cavad.promanage.dto.UserDto;
import com.cavad.promanage.dto.UserTaskResponse;
import com.cavad.promanage.model.User;
import com.cavad.promanage.service.RoleService;
import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@RequiredArgsConstructor
@Component
public class UserConverter {
        private final RoleService roleService;


    public RegistrationResponse convertToRegistrationResponse(User user){
        RegistrationResponse response= RegistrationResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
        return response;
    }

}
