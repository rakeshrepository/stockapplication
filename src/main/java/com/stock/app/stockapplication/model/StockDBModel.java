package com.stock.app.stockapplication.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "stocks")
@Entity
@NoArgsConstructor
public class StockDBModel extends AuditModel {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "st_id", nullable = false)
    @Id
    private Integer id;
    @Column(name = "st_name", nullable = false)
    private String name;
    @Column(name = "st_cur_price")
    private BigDecimal currentPrice;

    @Column(name = "st_lastUpdate", nullable = false)
    private Date lastUpdate;

    public StockDBModel(int id, String stockName, BigDecimal stockPrice, Date timeStamp) {
        this.id = id;
        this.name = stockName;
        this.currentPrice = stockPrice;
        this.lastUpdate = timeStamp;

    }
}
