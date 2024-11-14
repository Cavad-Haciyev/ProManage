package com.cavad.promanage.repository;

import com.cavad.promanage.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    Optional<Task> findById(String id);
}
