package com.project.todoapp.Service;

import com.project.todoapp.Model.ProjectModel;

import java.util.List;

public interface ProjectService {

    ProjectModel createProject(String title, Long userId);

    List<ProjectModel> getAllProjectsByUser(Long userId);

    ProjectModel getProjectById(long projectId);

    ProjectModel updateProject(long projectId, String projectTitle);

    String exportProjectSummary(Long id);
}
