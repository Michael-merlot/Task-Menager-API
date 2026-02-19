package com.taskmanager.service;

import com.taskmanager.exception.ProjectNotFoundException;
import com.taskmanager.model.*;
import com.taskmanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id){
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public Project createProject(Project project){
        if (project.getOwner() != null && project.getOwner().getId() != null){
            User owner = userRepository.findById(project.getOwner().getId()).orElseThrow(() -> new RuntimeException("Владелец не найден"));
        }
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project updateProject){
        Project findProject = projectRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Проект с ID " + id + " не найден"));

        findProject.setName(updateProject.getName());
        findProject.setDescription(updateProject.getDescription());

        return projectRepository.save(findProject);
    }

    public void deleteProject(Long id){
        getProjectById(id);
        projectRepository.deleteById(id);
    }

    public List<Project> getProjectByOwner(Long userId){
        User owner = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return projectRepository.findByOwner(owner);
    }

    public List<Project> getProjectsByStatus(ProjectStatus status){
        return projectRepository.findByStatus(status);
    }

}
