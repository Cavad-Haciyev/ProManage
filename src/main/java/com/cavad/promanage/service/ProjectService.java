package com.cavad.promanage.service;

import com.cavad.promanage.dto.ProjectDto;
import com.cavad.promanage.dto.ProjectResponse;
import com.cavad.promanage.dto.convertor.ProjectConvertor;
import com.cavad.promanage.exception.ProjectNotFoundException;
import com.cavad.promanage.model.Project;
import com.cavad.promanage.repository.ProjectRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectConvertor projectConvertor;

    public ProjectService(ProjectRepository projectRepository, ProjectConvertor projectConvertor) {
        this.projectRepository = projectRepository;
        this.projectConvertor = projectConvertor;
    }


    //CreateProject
    public String createProject(ProjectDto projectDto) {
        Project save = projectRepository.save(projectConvertor.convertToProject(projectDto));
        return "Project Successfully Created";
    }
    //GetAllProjects
    public List<ProjectResponse> getAllProject() {
        return projectRepository.
                findAll().stream()
                .map(projectConvertor::convertToProjectResponse)
                .toList();

    }
    //getProjectByName
    public ProjectResponse getProjectByName(String name) {
        Project project = projectRepository.findProjectByName(name).orElseThrow(ProjectNotFoundException::new);
        return projectConvertor.convertToProjectResponse(project);
    }

    //DeleteProject
    public String deleteProjectById(Long id) {
        Project project = projectRepository
                .findById(id)
                .orElseThrow(ProjectNotFoundException::new);

        projectRepository.delete(project);
        return "Project Successfully Deleted";
    }

    protected Project getProjectById(String id) {
        return projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
    }
}
