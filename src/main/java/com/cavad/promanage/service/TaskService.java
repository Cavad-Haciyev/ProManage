package com.cavad.promanage.service;

import com.cavad.promanage.dto.TaskDto;
import com.cavad.promanage.dto.TaskResponse;
import com.cavad.promanage.dto.UpdateTaskDto;
import com.cavad.promanage.dto.convertor.TaskConvertor;
import com.cavad.promanage.exception.TaskNotFoundException;
import com.cavad.promanage.model.Project;
import com.cavad.promanage.model.Task;
import com.cavad.promanage.model.User;
import com.cavad.promanage.repository.TaskRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskConvertor taskConvertor;

    public TaskService(TaskRepository taskRepository, UserService userService, ProjectService projectService, TaskConvertor taskConvertor) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectService = projectService;
        this.taskConvertor = taskConvertor;
    }

    //    CreatedTask
    public String createTask(TaskDto taskDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(name);
        Project projectById = projectService.getProjectById(taskDto.projectId());
        Task task = Task.builder()
                .name(taskDto.name())
                .description(taskDto.description())
                .completed(false)
                .startDate(taskDto.startDate())
                .endDate(taskDto.endDate())
                .user(user)
                .project(projectById)
                .build();
        Task save = taskRepository.save(task);

        return "Task Successfully Created";

    }

    //    CompletedTask
    public String completedTask(String id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        task.setCompleted(true);
        taskRepository.save(task);

        return task.getName() + " Successfully Completed";

    }

    //    UpdateTask
    public String updateTask(UpdateTaskDto updateTaskDto, String id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        task.setName(updateTaskDto.name());
        task.setDescription(updateTaskDto.description());
        task.setEndDate(updateTaskDto.endDate());

        taskRepository.save(task);

        return task.getName() + "Successfully Updated";

    }

    //    GetAllOngoingTasks
    public List<TaskResponse> getAllOngoingTasks() {
        List<Task> collect = taskRepository.findAll()
                .stream()
                .filter(task -> !task.getCompleted())
                .toList();
        return collect.stream()
                .map(taskConvertor::convertToTaskResponse)
                .collect(Collectors.toList());
    }


//        DeleteTask

    public String deleteTask(String id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepository.delete(task);
        return task.getName() + " Successfully Deleted";
    }

    //    GetTaskById
    public TaskResponse getTaskById(String id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return taskConvertor.convertToTaskResponse(task);
    }

}
