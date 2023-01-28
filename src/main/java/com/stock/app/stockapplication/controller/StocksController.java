package com.stock.app.stockapplication.controller;

import com.stock.app.stockapplication.model.StockDBModel;
import com.stock.app.stockapplication.model.requestdto.StockRequestDTO;
import com.stock.app.stockapplication.model.responsedto.StockResponseDTO;
import com.stock.app.stockapplication.service.IStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestControllerAdvice
@RequestMapping("/api")
@AllArgsConstructor
public class StocksController {
    private IStockService stockService;

    @Operation(summary = "Get all stocks based on pagination request.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of stocks retrieved based on the pagination requirements.",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))}),
            @ApiResponse(responseCode = "404", description = "Pagination content is empty", content = @Content)})
    @GetMapping(value = "/stocks")
    public Page<StockDBModel> getAllStocks(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        return stockService.getAllStocks(page, size);
    }

    @Operation(summary = "Create a stock resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "It generates the stock resource for successful request.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Stock request not meeting the requirements.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    @PostMapping(value = "/stocks", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StockResponseDTO> createStock(@RequestBody StockRequestDTO stockRequest) {
        return new ResponseEntity<>(stockService.createStock(stockRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve a stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get the stock from database.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StockResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Given stock id is not present in database.", content = @Content)})
    @GetMapping(value = "/stocks/{stockId}")
    public ResponseEntity<StockResponseDTO> getStock(@PathVariable("stockId") int stockId) {
        return new ResponseEntity<>(stockService.getStock(stockId), HttpStatus.OK);
    }

    @Operation(summary = "Update the particular stock only with price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the stock price",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "Given stock id is not present in database.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    @PatchMapping(value = "/stocks/{stockId}/{price}")
    public ResponseEntity<StockResponseDTO> updateStock(@PathVariable("stockId") int stockId, @PathVariable("price") BigDecimal price) {
        return new ResponseEntity<>(stockService.updateStock(stockId, price), HttpStatus.OK);
    }

    @Operation(summary = "Delete the stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully delete the stock based on id.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    @DeleteMapping(value = "/stocks/{stockId}")
    public ResponseEntity<Void> deleteStock(@PathVariable("stockId") int stockId) {
        stockService.deleteStock(stockId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
