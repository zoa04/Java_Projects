package com.finance.controller;

import com.finance.model.Category;
import com.finance.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository repo;
    public CategoryController(CategoryRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Category> all() { return repo.findAll(); }

    @PostMapping
    public Category create(@RequestBody Category c) { return repo.save(c); }
}
