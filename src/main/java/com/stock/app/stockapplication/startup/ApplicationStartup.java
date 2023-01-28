package com.stock.app.stockapplication.startup;

import com.stock.app.stockapplication.model.StockDBModel;
import com.stock.app.stockapplication.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ApplicationStartup implements CommandLineRunner {

    private StockRepository stockRepository;

    @Override
    public void run(String... args) throws Exception {
        log.debug("Initial records insertion started");
        StockDBModel stock1 = new StockDBModel(1, "amazon", new BigDecimal(325.45), new Date());
        StockDBModel stock2 = new StockDBModel(2, "meta", new BigDecimal(100.62), new Date());
        StockDBModel stock3 = new StockDBModel(3, "apple", new BigDecimal(90.12), new Date());
        StockDBModel stock4 = new StockDBModel(4, "dander", new BigDecimal(259.89), new Date());
        StockDBModel stock5 = new StockDBModel(5, "cisco", new BigDecimal(321.74), new Date());

        List<StockDBModel> stockList = Arrays.asList(stock1, stock2, stock3, stock4, stock5);
        //TODO how to avoid inserting these entries again to database after inserting.
        List<StockDBModel> totalRecords = stockRepository.saveAll(stockList);

        log.debug("{} Total records inserted into db.", totalRecords.size());
    }
}
