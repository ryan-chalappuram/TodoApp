package com.project.todoapp.Repository;

import com.project.todoapp.Model.TodoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoModel,Long> {
    List<TodoModel> findByProjectId(long projectId);
}
