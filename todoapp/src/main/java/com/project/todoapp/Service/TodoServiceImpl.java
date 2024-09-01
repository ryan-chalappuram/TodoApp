package com.project.todoapp.Service;

import com.project.todoapp.Model.ProjectModel;
import com.project.todoapp.Model.TodoModel;
import com.project.todoapp.Repository.ProjectRepository;
import com.project.todoapp.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public TodoModel createTodo(Long projectId, String description) {
        ProjectModel project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        TodoModel todo = new TodoModel();
        todo.setDescription(description);
        todo.setStatus("Pending");
        todo.setCreatedDate(LocalDateTime.now());
        todo.setProject(project);

        return todoRepository.save(todo);
    }

    @Override
    public List<TodoModel> getTodosByProject(long projectId) {
        return todoRepository.findByProjectId(projectId);
    }
    @Override
    public TodoModel getTodoById(long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));
    }

    @Override
    public void deleteTodoById(long todoId) {
        TodoModel todo = getTodoById(todoId);
        todoRepository.delete(todo);
    }

    @Override
    public TodoModel updateTodo(long todoId,String description,String status) {
        TodoModel todo = getTodoById(todoId);
        todo.setUpdatedDate(LocalDateTime.now());
        todo.setDescription(description);
        todo.setStatus(status);
        return todoRepository.save(todo);
    }
}
