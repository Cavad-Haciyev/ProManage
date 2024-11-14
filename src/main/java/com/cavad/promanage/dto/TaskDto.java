package com.cavad.promanage.dto;

import com.cavad.promanage.model.User;

import java.time.LocalDateTime;

public record TaskDto(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String projectId




) {
}
