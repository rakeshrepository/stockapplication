package com.stock.app.stockapplication.model.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StockRequestDTO {
    private String name;
    private BigDecimal currentPrice;
    private Date timestamp;

    public StockRequestDTO(String stockName, BigDecimal stockCurrentPrice, Date timestamp) {
        this.name = stockName;
        this.currentPrice = stockCurrentPrice;
        this.timestamp = timestamp;
    }
}
