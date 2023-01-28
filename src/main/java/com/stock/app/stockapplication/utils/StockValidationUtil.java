package com.stock.app.stockapplication.utils;

import com.stock.app.stockapplication.constants.StockAppConstants;
import com.stock.app.stockapplication.exception.RequestValidationException;
import com.stock.app.stockapplication.model.StockDBModel;
import com.stock.app.stockapplication.model.requestdto.StockRequestDTO;
import com.stock.app.stockapplication.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class StockValidationUtil {

    private StockRepository stockRepository;

    /**
     * This method is used to validate the stock request dto.
     * It returns all the error details to user with validation exception.
     *
     * @param stockRequest
     */
    public void validateStockRequest(StockRequestDTO stockRequest) {

        Map<String, String> requestErrorValidation = new LinkedHashMap<>();
        Map<String, String> nameErrorValidation = new LinkedHashMap<>();

        if (StringUtils.isBlank(stockRequest.getName()) || stockRequest.getName().length() >= 255 || !Pattern.matches(StockAppConstants.NAME_REGX, stockRequest.getName())) {
            requestErrorValidation.put("Name", String.format("Stock name [%s] is empty/null or exceeding 255 characters or pattern not matched.", stockRequest.getName()));
        } else {
            String stockNameTrim = stockRequest.getName().trim();
            StockDBModel stockDb = stockRepository.findByNameContaining(stockNameTrim);
            if (!ObjectUtils.isEmpty(stockDb) && !StringUtils.isEmpty(stockDb.getName()) && stockDb.getName().equals(stockNameTrim)) {
                nameErrorValidation.put("Name", String.format("Stock name [%s] is already exists.", stockRequest.getName()));
            }
        }

        if (stockRequest.getCurrentPrice().doubleValue() <= StockAppConstants.ZERO_INT) {
            requestErrorValidation.put("Price", String.format("Stock price [%s] is less than or equal to zero", stockRequest.getCurrentPrice().doubleValue()));
        }
        if (ObjectUtils.isEmpty(stockRequest.getTimestamp())) {
            requestErrorValidation.put("Timestamp", String.format("Stock timestamp [%s] is empty or not > current date ", stockRequest.getTimestamp()));
        }

        if (!CollectionUtils.isEmpty(requestErrorValidation)) {
            throw new RequestValidationException("Request validation error", requestErrorValidation, HttpStatus.BAD_REQUEST);
        }
        if (!CollectionUtils.isEmpty(nameErrorValidation)) {
            throw new RequestValidationException("Request validation error", nameErrorValidation, HttpStatus.CONFLICT);
        }


    }

    /**
     * This method is used to validate the pagination request.
     *
     * @param page
     * @param size
     */
    public void validatePaginationRequest(int page, int size) {

        /** pagination must hold gad rails for max page size.*/
        if (page < StockAppConstants.ZERO_INT || size < StockAppConstants.ONE_INT) {
            throw new IllegalArgumentException(String.format("Page number %s or/and page size %s is not valid", page, size));
        }
    }
}
