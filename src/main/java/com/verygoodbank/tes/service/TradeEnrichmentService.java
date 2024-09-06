package com.verygoodbank.tes.service;

import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.utlls.CSVUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TradeEnrichmentService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public List<Trade> enrichTrades(Path productsFilePath, Path tradesFilePath) throws IOException {
        Map<Integer, String> products = CSVUtil.readProducts(productsFilePath);
        List<Trade> trades = CSVUtil.readTrades(tradesFilePath);

        return trades.parallelStream()
                .filter(this::isValidDate)
                .peek(trade -> {
                    String productName = products.getOrDefault(trade.getProductId(), "Missing Product Name");
                    if ("Missing Product Name".equals(productName)) {
                        System.err.println("Missing product name for product_id: " + trade.getProductId());
                    }
                    trade.setProductName(productName);
                })
                .collect(Collectors.toList());
    }

    private boolean isValidDate(Trade trade) {
        try {
            LocalDate.parse(trade.getDate(), DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format for trade: " + trade);
            return false;
        }
    }
}