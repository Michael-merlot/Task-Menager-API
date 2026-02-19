package com.taskmanager.repository;

import com.taskmanager.model.Project;
import com.taskmanager.model.ProjectStatus;
import com.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByOwner(User owner);

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByOwnerAndStatus(User owner, ProjectStatus status);

    long countByOwner(User owner);

}
