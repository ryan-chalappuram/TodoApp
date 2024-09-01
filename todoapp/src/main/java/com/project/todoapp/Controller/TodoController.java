package com.project.todoapp.Controller;

import com.project.todoapp.Model.TodoModel;
import com.project.todoapp.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/project/{projectId}")
    public ResponseEntity<TodoModel> createTodo(@PathVariable long projectId, @RequestParam String description){
        TodoModel todo = todoService.createTodo(projectId, description);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TodoModel>> getTodosByProject(@PathVariable long projectId){
        List<TodoModel> todos = todoService.getTodosByProject(projectId);
        return ResponseEntity.ok(todos);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoModel> updateTodo(@PathVariable long todoId,@RequestParam String description, @RequestParam(defaultValue = "pending") String status ){
        TodoModel todo = todoService.updateTodo(todoId,description,status);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> updateTodo(@PathVariable long todoId) {
        todoService.deleteTodoById(todoId);
        return ResponseEntity.ok("Deleted");
    }
}
