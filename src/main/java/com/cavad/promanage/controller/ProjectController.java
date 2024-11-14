package com.cavad.promanage.controller;

import com.cavad.promanage.baseresponse.ResponseModelService;
import com.cavad.promanage.dto.ProjectDto;
import com.cavad.promanage.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "http://localhost:3000")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("/createProject")
    public ResponseEntity<Object> createProject(
            @RequestBody ProjectDto projectDto) {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                projectService.createProject(projectDto),
                "Successfully",
                HttpStatus.OK);


    }

    @GetMapping("/getAllProject")
    public ResponseEntity<Object> getAllProject() {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                projectService.getAllProject(),
                "Successfully",
                HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getProjectByName(@PathVariable String name) {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                projectService.getProjectByName(name),
                "Successfully",
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable Long id) {
        return ResponseModelService
                .responseBuilder(LocalDateTime.now(),
                        projectService.deleteProjectById(id),
                        "Successfully",
                        HttpStatus.OK);
    }

}
