package com.example.produtoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.produtoapi.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
