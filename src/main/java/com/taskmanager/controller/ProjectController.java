package com.taskmanager.controller;

import com.taskmanager.model.Project;
import com.taskmanager.model.Task;
import com.taskmanager.service.ProjectService;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.OnOpen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Проекты", description = "Управление проектами")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    @Operation(summary = "Получить все проекты")
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить проект по ID")
    public Project getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать проект")
    public Project createProject(@Valid @RequestBody Project project){
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить проект")
    public Project updateProject(@PathVariable Long id, @Valid @RequestBody Project project){
        return projectService.updateProject(id, project);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить проект")
    public void deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
    }

    @GetMapping("/my")
    @Operation(summary = "Мои проекты", description = "Возвращает проекты текущего пользователя")
    public List<Project> getMyProjects() {
        return projectService.getMyProjects();
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Получить задачу по ID проекта")
    public List<Task> getTasksById(@PathVariable Long id){
        return taskService.getTasksByProject(id);
    }
}
