package com.cavad.promanage.dto;

import java.time.LocalDateTime;

public record ProjectDto(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate) {
}
