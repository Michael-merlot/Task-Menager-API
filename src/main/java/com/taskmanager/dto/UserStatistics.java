package com.taskmanager.dto;

public class UserStatistics {

    private int totalProjects;
    private int activeProjects;
    private int archivedProjects;

    private int totalTasks;
    private int todoTasks;
    private int inProgressTasks;
    private int doneTasks;
    private int overdueTasks;

    public UserStatistics() {}

    public int getTotalProjects() { return totalProjects; }
    public void setTotalProjects(int totalProjects) { this.totalProjects = totalProjects; }
    public int getActiveProjects() { return activeProjects; }
    public void setActiveProjects(int activeProjects) { this.activeProjects = activeProjects; }
    public int getArchivedProjects() { return archivedProjects; }
    public void setArchivedProjects(int archivedProjects) { this.archivedProjects = archivedProjects; }
    public int getTotalTasks() { return totalTasks; }
    public void setTotalTasks(int totalTasks) { this.totalTasks = totalTasks; }
    public int getTodoTasks() { return todoTasks; }
    public void setTodoTasks(int todoTasks) { this.todoTasks = todoTasks; }
    public int getInProgressTasks() { return inProgressTasks; }
    public void setInProgressTasks(int inProgressTasks) { this.inProgressTasks = inProgressTasks; }
    public int getDoneTasks() { return doneTasks; }
    public void setDoneTasks(int doneTasks) { this.doneTasks = doneTasks; }
    public int getOverdueTasks() { return overdueTasks; }
    public void setOverdueTasks(int overdueTasks) { this.overdueTasks = overdueTasks; }
}