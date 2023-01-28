package com.stock.app.stockapplication.repository;

import com.stock.app.stockapplication.model.StockDBModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockDBModel, Integer> {
    Page<StockDBModel> findByNameContaining(String name, Pageable pageable);

    StockDBModel findByNameContaining(String name);
}
