package com.cavad.promanage.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record TaskUserResponse(
        String name,
        String description,
        Boolean completed,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
