package com.cavad.promanage.dto;

import java.time.LocalDateTime;

public record UpdateTaskDto(

        String name,
        String description,
        LocalDateTime endDate
) {
}
