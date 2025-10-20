package com.finance.controller;

import com.finance.model.Category;
import com.finance.model.FinanceTransaction;
import com.finance.repository.CategoryRepository;
import com.finance.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionRepository txRepo;
    private final CategoryRepository catRepo;
    public TransactionController(TransactionRepository txRepo, CategoryRepository catRepo) {
        this.txRepo = txRepo; this.catRepo = catRepo;
    }

    @GetMapping
    public List<FinanceTransaction> all() { return txRepo.findAll(); }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionRequest req) {
        Optional<Category> cat = catRepo.findById(req.getCategoryId());
        if (!cat.isPresent()) return ResponseEntity.badRequest().body("Category not found");
        FinanceTransaction tx = new FinanceTransaction();
        tx.setType(req.getType());
        tx.setAmount(req.getAmount());
        tx.setDate(req.getDate());
        tx.setCategory(cat.get());
        return ResponseEntity.ok(txRepo.save(tx));
    }

    public static class TransactionRequest {
        private com.finance.model.TransactionType type;
        private Double amount;
        private java.time.LocalDate date;
        private Long categoryId;
        public TransactionRequest() {}
        public com.finance.model.TransactionType getType() { return type; }
        public void setType(com.finance.model.TransactionType type) { this.type = type; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
        public java.time.LocalDate getDate() { return date; }
        public void setDate(java.time.LocalDate date) { this.date = date; }
        public Long getCategoryId() { return categoryId; }
        public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    }
}
