package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private List<com.example.demo.Todo> todos = new ArrayList<>();
    private Long idCounter = 1L;

    // Get all todos
    @GetMapping
    public ResponseEntity<List<com.example.demo.Todo>> getAllTodos() {
        return ResponseEntity.ok(todos);
    }

    // Add a new todo
    @PostMapping
    public ResponseEntity<String> addTodo(@RequestBody com.example.demo.Todo todo) {
        todo.setId(idCounter++);
        todos.add(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Todo added successfully!");
    }

    // Get a todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<com.example.demo.Todo> getTodoById(@PathVariable Long id) {
        return todos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Update a todo by ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable Long id, @RequestBody com.example.demo.Todo updatedTodo) {
        for (com.example.demo.Todo todo : todos) {
            if (todo.getId().equals(id)) {
                todo.setTitle(updatedTodo.getTitle());
                todo.setCompleted(updatedTodo.isCompleted());
                return ResponseEntity.ok("Todo updated successfully!");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found!");
    }

    // Delete a todo by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        boolean removed = todos.removeIf(todo -> todo.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok("Todo deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found!");
        }
    }
}
