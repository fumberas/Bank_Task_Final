package com.verygoodbank.tes;

import com.verygoodbank.tes.controller.TradeEnrichmentController;
import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.service.TradeEnrichmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TradeEnrichmentController.class)
public class TradeEnrichmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeEnrichmentService tradeEnrichmentService;

    private MockMultipartFile mockTradeFile;

    @BeforeEach
    public void setUp() {
        String tradeFileContent = "date,product_id,currency,price\n" +
                "20160101,1,EUR,10.0\n" +
                "20160101,2,EUR,20.1\n";
        mockTradeFile = new MockMultipartFile("tradeFile", "trades.csv", "text/csv", tradeFileContent.getBytes());
    }

    @Test
    public void testEnrichTrades() throws Exception {
        Trade trade1 = new Trade("20160101", 1, "EUR", 10.0);
        trade1.setProductName("Treasury Bills Domestic");

        Trade trade2 = new Trade("20160101", 2, "EUR", 20.1);
        trade2.setProductName("Corporate Bonds Domestic");

        List<Trade> mockEnrichedTrades = Arrays.asList(trade1, trade2);

        when(tradeEnrichmentService.enrichTrades(any(Path.class), any(Path.class))).thenReturn(mockEnrichedTrades);

        MvcResult mvcResult = mockMvc.perform(multipart("/api/v1/enrich")
                        .file(mockTradeFile))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponse = "[{\"productName\":\"Treasury Bills Domestic\",\"date\":\"20160101\",\"product_id\":1,\"currency\":\"EUR\",\"price\":10.0}," +
                "{\"productName\":\"Corporate Bonds Domestic\",\"date\":\"20160101\",\"product_id\":2,\"currency\":\"EUR\",\"price\":20.1}]";
        String actualResponse = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedResponse, actualResponse);
    }
}
