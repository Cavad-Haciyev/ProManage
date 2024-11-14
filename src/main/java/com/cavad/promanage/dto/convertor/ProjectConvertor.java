package com.cavad.promanage.dto.convertor;

import com.cavad.promanage.dto.ProjectDto;
import com.cavad.promanage.dto.ProjectResponse;
import com.cavad.promanage.model.Project;
import com.cavad.promanage.model.Task;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Builder
@RequiredArgsConstructor
@Component
public class ProjectConvertor {
    private final TaskConvertor taskConvertor;

    public Project convertToProject(ProjectDto projectDto){
        Project project = Project.builder()
                .name(projectDto.name())
                .description(projectDto.description())
                .startDate(projectDto.startDate())
                .endDate(projectDto.endDate())
                .build();
        return project;
    }

    public ProjectResponse convertToProjectResponse(Project project){
        ProjectResponse projectResponse = ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .starDate(project.getStartDate())
                .endDate(project.getEndDate())
                .tasks(project.getTasks().stream().map(taskConvertor::convertToTaskProjectResponse).collect(Collectors.toList()))
                .build();
        return projectResponse;
    }
}
