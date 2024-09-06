package com.verygoodbank.tes;

import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.service.TradeEnrichmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

//Used for testing the performance of the TradeEnrichmentService class for large files.
@SpringBootTest
public class TradeEnrichmentServicePerformanceTest {

    @Autowired
    private TradeEnrichmentService tradeEnrichmentService;

    private static final Path PRODUCTS_FILE = Path.of("src/main/resources/products.csv");
    private static final Path TRADES_FILE = Path.of("src/test/resources/trade.csv");

    @Test
    public void testEnrichTradesPerformance() throws IOException {
        long startTime = System.currentTimeMillis();
        List<Trade> enrichedTrades = tradeEnrichmentService.enrichTrades(PRODUCTS_FILE, TRADES_FILE);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time taken to enrich trades: " + duration + " ms");
        assertTrue(duration < 20, "Enriching trades took too long");
    }
}