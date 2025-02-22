package com.cavad.promanage.repository;

import com.cavad.promanage.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Optional<Project> findProjectByName(String name);
    Optional<Project> findById(String id);
}
