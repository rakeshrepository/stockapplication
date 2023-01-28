package com.stock.app.stockapplication.service;

import com.stock.app.stockapplication.exception.RequestValidationException;
import com.stock.app.stockapplication.exception.ResourceNotFoundException;
import com.stock.app.stockapplication.model.StockDBModel;
import com.stock.app.stockapplication.model.requestdto.StockRequestDTO;
import com.stock.app.stockapplication.model.responsedto.StockResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StockServiceImpTest extends IntegrationSetUpTest {

    @Autowired
    private IStockService stockService;

    @DisplayName("Successfully create stock")
    @Test
    public void create_Stock_Test() {
        StockRequestDTO stockRequest = new StockRequestDTO("Cisco", new BigDecimal(325.45), new Date());
        StockResponseDTO stockRes = stockService.createStock(stockRequest);
        StockResponseDTO stockIdRes = stockService.getStock(stockRes.getId());
        System.out.println("Date :" + stockIdRes.getLastUpdate());
        assertTrue(stockIdRes.getName().equals(stockRes.getName()));
    }

    @DisplayName("Unable to create stock with existing same name")
    @Test
    public void stock_Already_Exist_Create_StockTest() {
        StockRequestDTO stockRequest = new StockRequestDTO("amazon", new BigDecimal(325.45), new Date());
        assertThrows(RequestValidationException.class, () -> stockService.createStock(stockRequest));
    }

    @DisplayName("Request validation error for create stock")
    @Test
    public void stock_Creation_Validation_Error_Test() {
        StockRequestDTO stockRequest = new StockRequestDTO("$Sign%ovar*", new BigDecimal(00.00), null);
        assertThrows(RequestValidationException.class, () -> stockService.createStock(stockRequest));
    }

    @DisplayName("Retrieve stock details based on stock id")
    @Test
    public void stock_get_stock_by_id_Test() {
        StockResponseDTO stockResDB = stockService.getStock(1);
        assertNotNull(stockResDB);
        assertEquals(1, stockResDB.getId());
    }

    @DisplayName("Stock id is not found")
    @Test
    public void stock_not_found_by_id_Test() {
        assertThrows(ResourceNotFoundException.class, () -> stockService.getStock(100));
    }

    @DisplayName("Update stock price based on stock Id")
    @Test
    public void update_stock_by_id_with_price_Test() {
        StockResponseDTO stockResDb = stockService.updateStock(1, new BigDecimal(499.50));
        StockResponseDTO stockReadRes = stockService.getStock(1);
        assertNotNull(stockResDb);
        assertEquals(stockReadRes.getName(), stockResDb.getName());
    }

    @DisplayName("Update stock id not found")
    @Test
    public void update_stock_by_id_not_found_Test() {
        boolean flag = false;
        String message = null;
        try {
            stockService.updateStock(104, new BigDecimal(499.50));
        } catch (ResourceNotFoundException ex) {
            message = ex.getMessage();
            flag = true;
        }
        assertTrue(flag);
        assertNotNull(message);

    }

    @DisplayName("Stock id not present for delete")
    @Test
    public void delete_stock_id_not_found_Test() {
        boolean flag = false;
        try {
            stockService.deleteStock(4000);
        } catch (Exception ex) {
            flag = true;
        }
        assertTrue(flag);
    }

    @DisplayName("Get all stocks with pagination for empty response")
    @Test
    public void get_empty_response_stocks_Test() {
        String exceptionMsg = null;
        int page = 10;
        int size = 50;

        try {
            Page<StockDBModel> paginationResponse = stockService.getAllStocks(page, size);
        } catch (ResourceNotFoundException ex) {
            exceptionMsg = ex.getMessage();
        }
        assertEquals(String.format("Pagination content is empty for given page %s and size %s", page, size), exceptionMsg);
    }

    @DisplayName("Get all the stocks for given pagination without stockName")
    @Test
    public void get_all_stock_for_pagination_without_name_Test() {
        insertRecordsIntoDb();
        Page<StockDBModel> paginationResponse = stockService.getAllStocks(0, 5);
        assertTrue(paginationResponse.getContent().size() > 0);

    }

    private void insertRecordsIntoDb() {
        StockRequestDTO stockRequest1 = new StockRequestDTO("Mock", new BigDecimal(325.45), new Date());
        stockService.createStock(stockRequest1);
        StockRequestDTO stockRequest2 = new StockRequestDTO("Accenture", new BigDecimal(325.45), new Date());
        stockService.createStock(stockRequest2);
        StockRequestDTO stockRequest3 = new StockRequestDTO("MindTree", new BigDecimal(325.45), new Date());
        stockService.createStock(stockRequest3);
    }


}
