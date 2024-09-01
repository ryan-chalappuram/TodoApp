package com.project.todoapp.Service;

import com.project.todoapp.Model.ProjectModel;
import com.project.todoapp.Model.TodoModel;
import com.project.todoapp.Model.UserModel;
import com.project.todoapp.Repository.ProjectRepository;
import com.project.todoapp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ProjectModel createProject(String title, Long userId){
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ProjectModel projectModel = new ProjectModel();
        projectModel.setTitle(title);
        projectModel.setUser(user);
        projectModel.setCreatedDate(LocalDateTime.now());

        return projectRepository.save(projectModel);
    }

    @Override
    public List<ProjectModel> getAllProjectsByUser(Long userId){
        return projectRepository.findByUserId(userId);
    }

    @Override
    public ProjectModel getProjectById(long projectId){
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }

    @Override
    public ProjectModel updateProject(long projectId, String projectTitle) {
        ProjectModel project = getProjectById(projectId);
        project.setTitle(projectTitle);
        return projectRepository.save(project);
    }

    @Override
    public String exportProjectSummary(Long id) {
        ProjectModel project = projectRepository.findById(id)
                .orElse(null);

        if (project == null) {
            return null;
        }

        List<TodoModel> completedTodos = project.getTodos().stream()
                .filter(todo -> "complete".equals(todo.getStatus()))
                .collect(Collectors.toList());
        int totalTodos = project.getTodos().size();

        StringBuilder markdownContent = new StringBuilder();
        markdownContent.append("**").append(project.getTitle()).append("**\n\n");
        markdownContent.append("**Summary:** ").append(completedTodos.size()).append(" / ").append(totalTodos).append(" todos completed.\n\n");

        markdownContent.append("**Pending Todos**\n\n");
        project.getTodos().forEach(todo -> {
            if ("pending".equals(todo.getStatus())) {
                markdownContent.append("- [ ] ").append(todo.getDescription()).append("\n");
            }
        });

        markdownContent.append("\n**Completed Todos**\n\n");
        completedTodos.forEach(todo -> {
            markdownContent.append("- [x] ").append(todo.getDescription()).append("\n");
        });

        return markdownContent.toString();
    }

}
