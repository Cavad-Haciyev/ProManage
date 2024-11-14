package com.cavad.promanage.dto;

import lombok.Builder;

@Builder
public record UpdateUserDto (
        String userName
){
}
