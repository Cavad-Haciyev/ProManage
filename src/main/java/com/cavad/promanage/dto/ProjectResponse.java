package com.cavad.promanage.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProjectResponse(
        String id,
        String name,
        String description,
        LocalDateTime starDate,
        LocalDateTime endDate,
        List<TaskProjectResponse> tasks) {
}
