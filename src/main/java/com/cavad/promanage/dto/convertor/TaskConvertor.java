package com.cavad.promanage.dto.convertor;

import com.cavad.promanage.dto.TaskProjectResponse;
import com.cavad.promanage.dto.TaskResponse;
import com.cavad.promanage.dto.TaskUserResponse;
import com.cavad.promanage.model.Task;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@RequiredArgsConstructor
@Component
public class TaskConvertor {

    public TaskResponse convertToTaskResponse(Task task) {
        return TaskResponse.builder()
                .name(task.getName())
                .description(task.getDescription())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .user(task.getUser())
                .project(task.getProject())
                .build();
    }

    public TaskProjectResponse convertToTaskProjectResponse(Task task) {
        return TaskProjectResponse.builder()
                .name(task.getName())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .userId(task.getUser().getId())
                .projectId(task.getProject().getId())
                .build();
    }

    public TaskUserResponse convertToTaskUserResponse(Task task) {
        return TaskUserResponse.builder()
                .name(task.getName())
                .completed(task.getCompleted())
                .description(task.getDescription())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .build();
    }
}
