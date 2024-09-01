package com.project.todoapp.Controller;


import com.project.todoapp.Model.ProjectModel;
import com.project.todoapp.Service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectModel> createProject(@RequestParam String title, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ProjectModel projectModel = projectService.createProject(title, userId);
        return ResponseEntity.ok(projectModel);
    }

    @GetMapping
    public ResponseEntity<List<ProjectModel>> getAllProjectsByUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<ProjectModel> project = projectService.getAllProjectsByUser(userId);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectModel> getProjectById(@PathVariable Long projectId){
        ProjectModel projectModel = projectService.getProjectById(projectId);
        return ResponseEntity.ok(projectModel);
    }


    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectModel> updateProject(@PathVariable long projectId, @RequestParam String newTitle, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ProjectModel project = projectService.getProjectById(projectId);
        if (project.getUser().getId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ProjectModel updatedProject = projectService.updateProject(projectId, newTitle);
        return ResponseEntity.ok(updatedProject);
    }

    @GetMapping("/{id}/export")
    public void exportProjectSummary(@PathVariable Long id, HttpServletResponse response) {
        try {
            String markdownContent = projectService.exportProjectSummary(id);

            if (markdownContent == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Project not found");
                return;
            }

            response.setHeader("Content-Disposition", "attachment; filename=\"" + id + ".md\"");
            response.setContentType("text/markdown");

            response.getWriter().write(markdownContent);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
