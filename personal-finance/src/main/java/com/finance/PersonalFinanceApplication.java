package com.finance;

import com.finance.model.Category;
import com.finance.model.FinanceTransaction;
import com.finance.model.TransactionType;
import com.finance.repository.CategoryRepository;
import com.finance.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class PersonalFinanceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonalFinanceApplication.class, args);
    }

    // Sample data loader
    @Bean
    CommandLineRunner init(CategoryRepository catRepo, TransactionRepository txRepo) {
        return args -> {
            if (catRepo.count() == 0) {
                Category food = catRepo.save(new Category(null, "Food"));
                Category salary = catRepo.save(new Category(null, "Salary"));
                Category transport = catRepo.save(new Category(null, "Transport"));

                txRepo.save(new FinanceTransaction(null, TransactionType.EXPENSE, 45.50, LocalDate.now().minusDays(2), food));
                txRepo.save(new FinanceTransaction(null, TransactionType.INCOME, 2500.0, LocalDate.now().minusDays(10), salary));
                txRepo.save(new FinanceTransaction(null, TransactionType.EXPENSE, 15.0, LocalDate.now().minusDays(1), transport));
            }
        };
    }
}
