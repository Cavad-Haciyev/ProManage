package com.cavad.promanage.dto;

import com.cavad.promanage.model.Project;
import com.cavad.promanage.model.User;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record TaskResponse(

        String name,
        String description,
        Boolean completed,
        LocalDateTime startDate,
        LocalDateTime endDate,
        User user,
        Project project




){

}
