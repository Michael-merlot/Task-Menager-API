package com.taskmanager.service;

import com.taskmanager.exception.*;
import com.taskmanager.model.*;
import com.taskmanager.repository.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User testUser;
    private Project testProject;
    private Task testTask1;
    private Task testTask2;
    private Task testTask3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User("Иван", "ivan@mail.com", "123", UserRole.USER);
        testUser.setId(1L);

        testProject = new Project("Веб-сайт", "Описание", testUser);
        testProject.setId(1L);
        testProject.setStatus(ProjectStatus.ACTIVE);

        testTask1 = new Task("Разработать главную страницу", "Создать дизайн главной страницы", testProject);
        testTask1.setId(1L);
        testTask1.setStatus(TaskStatus.TODO);
        testTask1.setPriority(TaskPriority.HIGH);
        testTask1.setAssignee(testUser);
        testTask1.setDueDate(LocalDate.now().plusDays(7));
        testTask1.setCreatedAt(LocalDateTime.now());

        testTask2 = new Task("Настроить базу данных", "PostgreSQL настройка", testProject);
        testTask2.setId(2L);
        testTask2.setStatus(TaskStatus.IN_PROGRESS);
        testTask2.setPriority(TaskPriority.CRITICAL);
        testTask2.setAssignee(testUser);
        testTask2.setDueDate(LocalDate.now().plusDays(3));
        testTask2.setCreatedAt(LocalDateTime.now());

        testTask3 = new Task("Написать тесты", "JUnit тесты для всех сервисов", testProject);
        testTask3.setId(3L);
        testTask3.setStatus(TaskStatus.DONE);
        testTask3.setPriority(TaskPriority.MEDIUM);
        testTask3.setDueDate(LocalDate.now().minusDays(2)); // Просроченная
        testTask3.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Получить все задачи")
    void getAllTasks_ShouldReturnList() {
        List<Task> tasks = Arrays.asList(testTask1, testTask2, testTask3);
        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> result = taskService.getAllTasks();
        assertEquals(3, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получить задачу по ID - успех")
    void getTaskById_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask1));
        Task result = taskService.getTaskById(1L);
        assertNotNull(result);
        assertEquals("Разработать главную страницу", result.getTitle());
        assertEquals(TaskStatus.TODO, result.getStatus());
        assertEquals(TaskPriority.HIGH, result.getPriority());
    }

    @Test
    @DisplayName("Изменить статус задачи")
    void updateTaskStatus_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask1));
        when(taskRepository.save(testTask1)).thenReturn(testTask1);
        Task result = taskService.updateTaskStatus(1L, TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        verify(taskRepository, times(1)).save(testTask1);
    }

    @Test
    @DisplayName("Назначить задачу пользователю")
    void assignTaskToUser_Success() {
        User newUser = new User("Мария", "maria@mail.com", "456", UserRole.USER);
        newUser.setId(2L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(newUser));
        when(taskRepository.save(testTask1)).thenReturn(testTask1);
        Task result = taskService.assignTaskToUser(1L, 2L);
        assertEquals(newUser, result.getAssignee());
        verify(taskRepository, times(1)).save(testTask1);
    }

    @Test
    @DisplayName("Получить просроченные задачи")
    void getOverdueTasks_ShouldReturnOverdueTasks() {
        List<Task> overdueTasks = Arrays.asList(testTask3);
        when(taskRepository.findByDueDateBeforeAndStatusNot(any(LocalDate.class), eq(TaskStatus.DONE))).thenReturn(overdueTasks);
        List<Task> result = taskService.getOverdueTasks();
        assertEquals(1, result.size());
        assertTrue(result.get(0).getDueDate().isBefore(LocalDate.now()));
    }

    @Test
    @DisplayName("Получить задачи по приоритету")
    void getTasksByPriority_ShouldReturnFilteredTasks() {
        List<Task> highPriorityTasks = Arrays.asList(testTask1);
        when(taskRepository.findByPriority(TaskPriority.HIGH)).thenReturn(highPriorityTasks);
        List<Task> result = taskService.getTasksByPriority(TaskPriority.HIGH);
        assertEquals(1, result.size());
        assertEquals(TaskPriority.HIGH, result.get(0).getPriority());
    }

    @Test
    @DisplayName("Получить задачи по статусу")
    void getTasksByStatus_ShouldReturnFilteredTasks() {
        List<Task> todoTasks = Arrays.asList(testTask1);
        when(taskRepository.findByStatus(TaskStatus.TODO)).thenReturn(todoTasks);
        List<Task> result = taskService.getTasksByStatus(TaskStatus.TODO);
        assertEquals(1, result.size());
        assertEquals(TaskStatus.TODO, result.get(0).getStatus());
    }
}