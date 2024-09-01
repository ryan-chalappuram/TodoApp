package com.project.todoapp.Service;

import com.project.todoapp.Model.TodoModel;

import java.util.List;

public interface TodoService {
    TodoModel createTodo(Long projectId,String description);

    List<TodoModel> getTodosByProject(long projectId);

    TodoModel updateTodo(long todoId,String description, String status);
    TodoModel getTodoById(long todoId);

    void deleteTodoById(long todoId);
}
