package com.verygoodbank.tes.utlls;

import com.verygoodbank.tes.model.Trade;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// Used for testing the performance of the CSVUtil class for large files.
public class CSVUtilTest {

    private static final Path PRODUCTS_FILE = Path.of("src/main/resources/products.csv");
    private static final Path TRADES_FILE = Path.of("src/test/resources/trade.csv");

    @Test
    public void testReadProductsPerformance() throws IOException {
        long startTime = System.currentTimeMillis();
        Map<Integer, String> products = CSVUtil.readProducts(PRODUCTS_FILE);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time taken to read products: " + duration + " ms");
        assertTrue(duration < 20, "Reading products took too long");
    }

    @Test
    public void testReadTradesPerformance() throws IOException {
        long startTime = System.currentTimeMillis();
        List<Trade> trades = CSVUtil.readTrades(TRADES_FILE);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Time taken to read trades: " + duration + " ms");
        assertTrue(duration < 15, "Reading trades took too long");
    }
}