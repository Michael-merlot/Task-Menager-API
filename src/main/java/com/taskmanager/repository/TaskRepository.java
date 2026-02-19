package com.taskmanager.repository;

import com.taskmanager.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import com.taskmanager.model.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProject(Project project);

    List<Task> findByProjectId(Long projectId);

    List<Task> findByAssignee(User assignee);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByDueDateBeforeAndStatusNot(LocalDate date, TaskStatus status);

    List<Task> findByProjectAndStatus(Project project, TaskStatus status);

    List<Task> findByAssigneeAndStatus(User assignee, TaskStatus status);

    long countByProject(Project project);

    long countByAssignee(User assignee);
}
