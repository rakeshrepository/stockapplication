package com.stock.app.stockapplication.controller;

import com.stock.app.stockapplication.exception.RequestValidationException;
import com.stock.app.stockapplication.model.StockDBModel;
import com.stock.app.stockapplication.model.requestdto.StockRequestDTO;
import com.stock.app.stockapplication.model.responsedto.StockResponseDTO;
import com.stock.app.stockapplication.service.IStockService;
import com.stock.app.stockapplication.service.IntegrationSetUpTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class StocksControllerTest extends IntegrationSetUpTest {

    private MockMvc mvc;

    @Mock
    private IStockService service;

    @InjectMocks
    private StocksController stocksController;

    @BeforeEach
    public void setUpController() {
        mvc = MockMvcBuilders.standaloneSetup(stocksController)
                .build();
    }


    @DisplayName("get all stocks")
    @Test
    public void get_all_stocks() throws Exception {
        StockDBModel stockDBModel = new StockDBModel(1, "TestStock", new BigDecimal(345.87), new Date());
        when(service.getAllStocks(anyInt(), anyInt())).thenReturn(new PageImpl<>(List.of(stockDBModel)));
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/stocks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("create a stock")
    @Test
    public void create_stock_request() throws Exception {
        StockResponseDTO stockResponse = new StockResponseDTO(1, "TestStock", new BigDecimal(345.87), new Date());
        when(service.createStock(any(StockRequestDTO.class))).thenReturn(stockResponse);

        StockRequestDTO request = new StockRequestDTO("TestStock", new BigDecimal(459), new Date());

        mvc.perform(post("/api/stocks")
                        .content(new ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(status().isCreated());
    }

    @DisplayName("delete a stock")
    @Test
    public void delete_stock_by_id_test() throws Exception {
        doNothing().when(service).deleteStock(anyInt());
        mvc.perform(delete("/api/stocks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @DisplayName("get a stock")
    @Test
    public void retrieve_stock_by_id_test() throws Exception {
        doNothing().when(service).deleteStock(anyInt());
        mvc.perform(get("/api/stocks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("update a stock")
    @Test
    public void update_stock_by_id_with_price_test() throws Exception {
        doNothing().when(service).deleteStock(anyInt());
        mvc.perform(patch("/api/stocks/1/350.25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
