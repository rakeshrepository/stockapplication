package com.stock.app.stockapplication.model.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class StockResponseDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("currentPrice")
    private BigDecimal currentPrice;
    @JsonProperty("lastUpdate")
    private Date lastUpdate;

    public StockResponseDTO(int id, String name, BigDecimal currentPrice, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdate = lastUpdate;
    }
}
