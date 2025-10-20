package com.finance.controller;

import com.finance.model.FinanceTransaction;
import com.finance.repository.TransactionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final TransactionRepository txRepo;
    public ReportController(TransactionRepository txRepo) { this.txRepo = txRepo; }

    // Returns total spending (expenses) by category
    @GetMapping("/spending-by-category")
    public List<Map<String,Object>> spendingByCategory() {
        List<FinanceTransaction> all = txRepo.findAll();
        Map<String, Double> sums = new HashMap<>();
        for (FinanceTransaction t : all) {
            String cat = t.getCategory() != null ? t.getCategory().getName() : "Uncategorized";
            double amt = (t.getType() == com.finance.model.TransactionType.EXPENSE) ? t.getAmount() : -t.getAmount();
            sums.put(cat, sums.getOrDefault(cat, 0.0) + amt);
        }
        return sums.entrySet().stream().map(e -> {
            Map<String,Object> m = new HashMap<>();
            m.put("category", e.getKey());
            m.put("amount", e.getValue());
            return m;
        }).collect(Collectors.toList());
    }
}
