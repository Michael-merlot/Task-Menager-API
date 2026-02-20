package com.taskmanager.service;

import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.model.*;
import com.taskmanager.repository.*;
import com.taskmanager.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        if (task.getProject() != null && task.getProject().getId() != null) {
            Project project = projectRepository.findById(task.getProject().getId())
                    .orElseThrow(() -> new RuntimeException("Проект не найден"));
            task.setProject(project);
        }
        if (task.getAssignee() != null && task.getAssignee().getId() != null) {
            User assignee = userRepository.findById(task.getAssignee().getId())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            task.setAssignee(assignee);
        }

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updateTask){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
        task.setTitle(updateTask.getTitle());
        task.setDescription(updateTask.getDescription());
        task.setPriority(updateTask.getPriority());
        task.setDueDate(updateTask.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        getTaskById(id);
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> getTasksByAssignee(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return taskRepository.findByAssignee(user);
    }

    @Transactional
    public Task updateTaskStatus(Long id, TaskStatus status){
        Task task = getTaskById(id);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Transactional
    public Task assignTaskToUser(Long taskId, Long userId){
        Task task = getTaskById(taskId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        task.setAssignee(user);
        return taskRepository.save(task);
    }

    public List<Task> getOverdueTasks(){
        return taskRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), TaskStatus.DONE);
    }

    public List<Task> getTasksByPriority(TaskPriority priority){
        return taskRepository.findByPriority(priority);
    }

    public List<Task> getTasksByStatus(TaskStatus status){
        return taskRepository.findByStatus(status);
    }

    public List<Task> getMyTasks() {
        User currentUser = userService.getCurrentUser();
        return taskRepository.findByAssignee(currentUser);
    }

    public boolean isTaskOwner(Long taskId) {
        User currentUser = userService.getCurrentUser();
        Task task = getTaskById(taskId);
        return task.getAssignee() != null && task.getAssignee().getId().equals(currentUser.getId());
    }

    public boolean isProjectOwner(Long projectId) {
        User currentUser = userService.getCurrentUser();
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        return project.getOwner().getId().equals(currentUser.getId());
    }

    public UserStatistics getMyStatistics() {
        User currentUser = userService.getCurrentUser();
        List<Task> myTasks = taskRepository.findByAssignee(currentUser);
        List<Project> myProjects = projectRepository.findByOwner(currentUser);

        UserStatistics stats = new UserStatistics();
        stats.setTotalProjects(myProjects.size());
        stats.setActiveProjects((int) myProjects.stream()
                .filter(p -> p.getStatus() == ProjectStatus.ACTIVE)
                .count());
        stats.setArchivedProjects((int) myProjects.stream()
                .filter(p -> p.getStatus() == ProjectStatus.ARCHIVED)
                .count());
        stats.setTotalTasks(myTasks.size());
        stats.setTodoTasks((int) myTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.TODO)
                .count());
        stats.setInProgressTasks((int) myTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS)
                .count());
        stats.setDoneTasks((int) myTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.DONE)
                .count());
        stats.setOverdueTasks((int) myTasks.stream()
                .filter(t -> t.getDueDate() != null && t.getDueDate().isBefore(java.time.LocalDate.now()) && t.getStatus() != TaskStatus.DONE)
                .count());

        return stats;
    }
}
