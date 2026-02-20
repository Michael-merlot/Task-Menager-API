# Task Manager API

REST API для управления задачами и проектами.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![JWT](https://img.shields.io/badge/JWT-Auth-red)

## Технологии

- **Java 21**
- **Spring Boot 3.2.4**
  - Spring Web
  - Spring Data JPA
  - Spring Security
- **PostgreSQL** — база данных
- **Hibernate** — ORM
- **Maven** — сборка проекта
- **JWT (JSON Web Token)** — авторизация
- **BCrypt** — хеширование паролей
- **Swagger/OpenAPI** — документация API
- **JUnit 5 + Mockito** — тестирование

## Возможности

- Регистрация и вход в систему
- Управление пользователями (ADMIN)
- CRUD операции для пользователей, проектов и задач
- Статусы задач: TODO, IN_PROGRESS, DONE, CANCELLED
- Приоритеты: LOW, MEDIUM, HIGH, CRITICAL
- Назначение задач пользователям
- Фильтрация просроченных задач
- Личная статистика пользователя
- Пользователь видит только свои данные
- Валидация входных данных
- Обработка ошибок (GlobalExceptionHandler)
- Swagger документация
- Unit тесты

## Установка и запуск

### Требования

- Java 21+
- PostgreSQL 15+
- Maven 3.8+

### Шаги установки

**1. Клон репозитория:**
```bash
git clone https://github.com/username/taskmanager-api.git
cd taskmanager-api
```

**2. Создать БД:**
```sql
CREATE DATABASE taskmanager_db;
```

**3. Настройка `application.properties`:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager_db
spring.datasource.username=postgres
spring.datasource.password=пароль
```

**4. Запуск приложение:**
```bash
mvn spring-boot:run
```

**5. Swagger UI:**
```
http://localhost:8080/swagger-ui.html
```

## API Endpoints

### Авторизация
| Метод | URL | Описание |
|-------|-----|----------|
| POST | `/api/auth/register` | Регистрация |
| POST | `/api/auth/login` | Вход |
| GET | `/api/auth/me` | Текущий пользователь |

### Пользователи

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/users` | Все пользователи |
| GET | `/api/users/{id}` | Пользователь по ID |
| POST | `/api/users` | Создать пользователя |
| PUT | `/api/users/{id}` | Обновить пользователя |
| DELETE | `/api/users/{id}` | Удалить пользователя |
| GET | `/api/users/{id}/tasks` | Задачи пользователя |

### Проекты

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/projects` | Все проекты |
| GET | `/api/projects/{id}` | Проект по ID |
| POST | `/api/projects` | Создать проект |
| PUT | `/api/projects/{id}` | Обновить проект |
| DELETE | `/api/projects/{id}` | Удалить проект |
| GET | `/api/projects/{id}/tasks` | Задачи проекта |

### Задачи

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/tasks` | Все задачи |
| GET | `/api/tasks/{id}` | Задача по ID |
| GET | `/api/tasks/my/statistics` | Моя статистика |
| POST | `/api/tasks` | Создать задачу |
| PUT | `/api/tasks/{id}` | Обновить задачу |
| DELETE | `/api/tasks/{id}` | Удалить задачу |
| PATCH | `/api/tasks/{id}/status` | Изменить статус |
| PATCH | `/api/tasks/{id}/assign/{userId}` | Назначить исполнителя |
| GET | `/api/tasks/overdue` | Просроченные задачи |
| GET | `/api/tasks/priority/{priority}` | По приоритету |
| GET | `/api/tasks/status/{status}` | По статусу |

## Автор
- GitHub: [@Michael-merlot](https://github.com/Michael-merlot)
- Email: holevhuik@yandex.ru

## Лицензия

MIT License