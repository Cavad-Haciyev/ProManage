package com.cavad.promanage.controller;

import com.cavad.promanage.baseresponse.ResponseModelService;
import com.cavad.promanage.dto.TaskDto;
import com.cavad.promanage.dto.UpdateTaskDto;
import com.cavad.promanage.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/task")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/createTask")
    public ResponseEntity<Object> createTask(
            @RequestBody TaskDto taskDto) {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                taskService.createTask(taskDto)
                ,
                "Successfully",
                HttpStatus.OK);
    }
    @GetMapping("/ongoingTasks")
    public ResponseEntity<Object> getAllOngoingTask() {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                taskService.getAllOngoingTasks()
                ,
                "Successfully",
                HttpStatus.OK);


    }

    @GetMapping("/taskById")
    public ResponseEntity<Object> getTaskById(@PathVariable String id) {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                taskService.getTaskById(id)
                ,
                "Successfully",
                HttpStatus.OK);


    }


    @PutMapping("/completed/{id}")
    public ResponseEntity<Object> completeTask(@PathVariable String id) {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                taskService.completedTask(id)
                ,
                "Successfully",
                HttpStatus.OK);


    }

    @PutMapping("/updateTask/{id}")
    public ResponseEntity<Object> updateTask(@RequestBody UpdateTaskDto taskDto,
                                             @PathVariable String id) {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                taskService.updateTask(taskDto, id)
                ,
                "Successfully",
                HttpStatus.OK);


    }


    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<Object> deleteTask( @PathVariable String id) {

        return ResponseModelService.responseBuilder(
                LocalDateTime.now(),
                taskService.deleteTask(id)
                ,
                "Successfully",
                HttpStatus.OK);


    }

}
