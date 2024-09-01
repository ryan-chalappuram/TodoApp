package com.project.todoapp.Repository;

import com.project.todoapp.Model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel,Long> {
    List<ProjectModel> findByUserId(Long userId);
}
