package com.cavad.promanage.dto;

import com.cavad.promanage.model.Task;
import lombok.Builder;

import java.util.List;

@Builder
public record UserTaskResponse(

        List<TaskUserResponse> userTasks

) {

}
