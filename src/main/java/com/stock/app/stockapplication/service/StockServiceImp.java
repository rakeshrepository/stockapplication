package com.stock.app.stockapplication.service;

import com.stock.app.stockapplication.constants.StockAppConstants;
import com.stock.app.stockapplication.exception.ResourceNotFoundException;
import com.stock.app.stockapplication.mappers.StockMappers;
import com.stock.app.stockapplication.model.StockDBModel;
import com.stock.app.stockapplication.model.requestdto.StockRequestDTO;
import com.stock.app.stockapplication.model.responsedto.StockResponseDTO;
import com.stock.app.stockapplication.repository.StockRepository;
import com.stock.app.stockapplication.utils.StockValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class StockServiceImp implements IStockService {

    private StockRepository stockRepository;
    private StockValidationUtil stockValidationUtil;
    private StockMappers stockMappers;

    @Override
    public StockResponseDTO createStock(StockRequestDTO stockRequest) {

        stockValidationUtil.validateStockRequest(stockRequest);

        StockDBModel stockDBModel = stockMappers.mapRequestObjToResponseObj(stockRequest);

        StockDBModel dbStockRes = stockRepository.save(stockDBModel);

        StockResponseDTO stockResponse = new StockResponseDTO();
        BeanUtils.copyProperties(dbStockRes, stockResponse);
        return stockResponse;
    }

    @Override
    public StockResponseDTO getStock(int stockId) {
        StockDBModel dbStockRes = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(StockAppConstants.RESOURCE_NOT_FOUND, stockId)));

        StockResponseDTO stockResponse = new StockResponseDTO();
        BeanUtils.copyProperties(dbStockRes, stockResponse);
        return stockResponse;
    }

    @Override
    public StockResponseDTO updateStock(int stockId, BigDecimal price) {
        StockDBModel stockDb = stockRepository.findById(stockId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(StockAppConstants.RESOURCE_NOT_FOUND, stockId)));
        stockDb.setCurrentPrice(price);
        StockDBModel stockUpdatedDb = stockRepository.save(stockDb);
        StockResponseDTO stockResponse = new StockResponseDTO();
        BeanUtils.copyProperties(stockUpdatedDb, stockResponse);
        return stockResponse;
    }

    @Override
    public void deleteStock(int stockId) {
        try {
            stockRepository.deleteById(stockId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(String.format(StockAppConstants.RESOURCE_NOT_FOUND, stockId));
        }
    }

    @Override
    public Page<StockDBModel> getAllStocks(int page, int size) {

        stockValidationUtil.validatePaginationRequest(page, size);

        Pageable paging = PageRequest.of(page, size);

        Page<StockDBModel> stockDbList = stockRepository.findAll(paging);

        if (stockDbList.getContent().isEmpty()) {
            throw new ResourceNotFoundException(String.format(StockAppConstants.PAGE_CONTENT_NOT_FOUND, page, size));
        }
        return stockDbList;
    }
}
