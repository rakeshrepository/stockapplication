package com.stock.app.stockapplication.service;

import com.stock.app.stockapplication.model.StockDBModel;
import com.stock.app.stockapplication.model.requestdto.StockRequestDTO;
import com.stock.app.stockapplication.model.responsedto.StockResponseDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface IStockService {
    StockResponseDTO createStock(StockRequestDTO stockRequest);

    StockResponseDTO getStock(int stockId);

    StockResponseDTO updateStock(int stockId, BigDecimal price);

    void deleteStock(int stockId);

    Page<StockDBModel> getAllStocks(int page, int size);
}
