package com.verygoodbank.tes;

import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.service.TradeEnrichmentService;
import com.verygoodbank.tes.utlls.CSVUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TradeEnrichmentServiceTest {

    private TradeEnrichmentService tradeEnrichmentService;
    private MockitoSession mockitoSession;

    @BeforeEach
    public void setUp() {
        tradeEnrichmentService = new TradeEnrichmentService();
        mockitoSession = Mockito.mockitoSession()
                .strictness(Strictness.LENIENT)
                .startMocking();
    }

    @Test
    public void testEnrichTrades_withValidData() throws IOException {
        try (MockedStatic<CSVUtil> utilities = mockStatic(CSVUtil.class)) {
            Path productsFilePath = mock(Path.class);
            Path tradesFilePath = mock(Path.class);

            Map<Integer, String> mockProducts = Map.of(
                    1, "Treasury Bills Domestic",
                    2, "Corporate Bonds Domestic",
                    3, "REPO Domestic"
            );
            List<Trade> mockTrades = Arrays.asList(
                    new Trade("20160101", 1, "EUR", 10.0),
                    new Trade("20160101", 2, "EUR", 20.1),
                    new Trade("20160101", 3, "EUR", 30.34)
            );

            when(CSVUtil.readProducts(productsFilePath)).thenReturn(mockProducts);
            when(CSVUtil.readTrades(tradesFilePath)).thenReturn(mockTrades);

            List<Trade> enrichedTrades = tradeEnrichmentService.enrichTrades(productsFilePath, tradesFilePath);

            assertEquals(3, enrichedTrades.size());
            assertEquals("Treasury Bills Domestic", enrichedTrades.get(0).getProductName());
            assertEquals("Corporate Bonds Domestic", enrichedTrades.get(1).getProductName());
            assertEquals("REPO Domestic", enrichedTrades.get(2).getProductName());
        }
    }

    @Test
    public void testEnrichTrades_withInvalidDate() throws IOException {
        try (MockedStatic<CSVUtil> utilities = mockStatic(CSVUtil.class)) {

            Path productsFilePath = mock(Path.class);
            Path tradesFilePath = mock(Path.class);

            Map<Integer, String> mockProducts = Map.of(
                    1, "Treasury Bills Domestic"
            );
            List<Trade> mockTrades = Arrays.asList(
                    new Trade("20160101", 1, "EUR", 10.0),
                    new Trade("2016-01-01", 1, "EUR", 20.1)
            );

            when(CSVUtil.readProducts(productsFilePath)).thenReturn(mockProducts);
            when(CSVUtil.readTrades(tradesFilePath)).thenReturn(mockTrades);

            List<Trade> enrichedTrades = tradeEnrichmentService.enrichTrades(productsFilePath, tradesFilePath);

            assertEquals(1, enrichedTrades.size());
            assertEquals("Treasury Bills Domestic", enrichedTrades.get(0).getProductName());
        }
    }

    @Test
    public void testEnrichTrades_withMissingProductName() throws IOException {
        try (MockedStatic<CSVUtil> utilities = mockStatic(CSVUtil.class)) {
            Path productsFilePath = mock(Path.class);
            Path tradesFilePath = mock(Path.class);

            Map<Integer, String> mockProducts = Map.of(
                    1, "Treasury Bills Domestic"
            );
            List<Trade> mockTrades = Arrays.asList(
                    new Trade("20160101", 1, "EUR", 10.0),
                    new Trade("20160101", 99, "EUR", 35.34)
            );

            when(CSVUtil.readProducts(productsFilePath)).thenReturn(mockProducts);
            when(CSVUtil.readTrades(tradesFilePath)).thenReturn(mockTrades);

            List<Trade> enrichedTrades = tradeEnrichmentService.enrichTrades(productsFilePath, tradesFilePath);

            assertEquals(2, enrichedTrades.size());
            assertEquals("Treasury Bills Domestic", enrichedTrades.get(0).getProductName());
            assertEquals("Missing Product Name", enrichedTrades.get(1).getProductName());
        }
    }

    @AfterEach
    public void tearDown() {
        mockitoSession.finishMocking();
    }
}
