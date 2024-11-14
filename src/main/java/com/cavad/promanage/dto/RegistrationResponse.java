package com.cavad.promanage.dto;

import com.cavad.promanage.model.Role;
import lombok.Builder;

import java.util.List;

@Builder
public record RegistrationResponse(

        String id,
        String email,
        String userName,
        List<Role> roles

) {
}
