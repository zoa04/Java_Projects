package com.finance.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class FinanceTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private Double amount;
    private LocalDate date;

    @ManyToOne
    private Category category;

    public FinanceTransaction() {}

    public FinanceTransaction(Long id, TransactionType type, Double amount, LocalDate date, Category category) {
        this.id = id; this.type = type; this.amount = amount; this.date = date; this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public String toString() {
        return "FinanceTransaction{" + id + ":" + type + "," + amount + "," + date + ",cat=" + (category!=null?category.getName():null) + "}";
    }
}
