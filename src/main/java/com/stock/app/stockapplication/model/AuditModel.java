package com.stock.app.stockapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stock.app.stockapplication.constants.StockModelConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {StockModelConstants.CREATED_AT, StockModelConstants.UPDATED_AT},
        allowGetters = true)
@Getter
@Setter
public abstract class AuditModel {

    @Column(name = StockModelConstants.CREATED_AT, nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = StockModelConstants.UPDATED_AT, nullable = false)
    @LastModifiedDate
    private Date updatedAt;
}
