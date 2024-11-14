package com.cavad.promanage.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskProjectResponse(

        String name,
        String description,
        LocalDateTime startDate,
        Boolean completed,
        LocalDateTime endDate,
        String userId,
        String projectId


) {
}
