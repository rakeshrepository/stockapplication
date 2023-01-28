package com.stock.app.stockapplication.mappers;

import com.stock.app.stockapplication.model.StockDBModel;
import com.stock.app.stockapplication.model.requestdto.StockRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class StockMappers {

    public StockDBModel mapRequestObjToResponseObj(StockRequestDTO stockRequest) {
        StockDBModel stockDBModel = new StockDBModel();
        stockDBModel.setName(stockRequest.getName().trim());
        stockDBModel.setCurrentPrice(stockRequest.getCurrentPrice());
        stockDBModel.setLastUpdate(stockRequest.getTimestamp());
        return stockDBModel;
    }
}
