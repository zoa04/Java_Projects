package com.finance.repository;

import com.finance.model.FinanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<FinanceTransaction, Long> {
    List<FinanceTransaction> findByDateBetween(LocalDate start, LocalDate end);
}
