package com.taskmanager.controller;

import com.taskmanager.model.*;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.service.*;
import com.taskmanager.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Задачи", description = "Управление задачами")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Получить все задачи")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить задачу по ID")
    public Task getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать задачу")
    public Task createTask(@Valid @RequestBody Task task){
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить задачу")
    public Task updateTask(@PathVariable Long id, @Valid @RequestBody Task task){
        return taskService.updateTask(id, task);
    }

    @GetMapping("/my")
    @Operation(summary = "Мои задачи", description = "Возвращает задачи назначенные текущему пользователю")
    public List<Task> getMyTasks() {
        return taskService.getMyTasks();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить задачу")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Обновить статус задачи", description = "Обновить статус: TODO, PROGRESS, DONE, CANCELLED")
    public Task updateTaskStatus(@PathVariable Long id, @RequestBody Map<String, String> body){
        TaskStatus status = TaskStatus.valueOf(body.get("status"));
        return taskService.updateTaskStatus(id, status);
    }

    @GetMapping("/my/statistics")
    @Operation(summary = "Моя статистика", description = "Статистика задач и проектов текущего пользователя")
    public UserStatistics getMyStatistics() {
        return taskService.getMyStatistics();
    }

    @PatchMapping("/{id}/assign/{userId}")
    @Operation(summary = "Назначить исполнителя", description = "Назначить задачу конкретному пользователю")
    public Task assigneeToUser(@PathVariable Long id, @PathVariable Long userId){
        return taskService.assignTaskToUser(id, userId);
    }

    @GetMapping("/overdue")
    @Operation(summary = "Просроченные задачи", description = "Задачи, у которых dueDate прошла и статус не DONE")
    public List<Task> getOverdueTasks(){
        return taskService.getOverdueTasks();
    }

    @GetMapping("priority/{priority}")
    @Operation(summary = "Получить список задач по приоритету")
    public List<Task> getTasksByPriority(TaskPriority priority){
        return taskService.getTasksByPriority(priority);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Получить список задач по статусу")
    public List<Task> getTasksByStatus(TaskStatus status){
        return taskService.getTasksByStatus(status);
    }
}
