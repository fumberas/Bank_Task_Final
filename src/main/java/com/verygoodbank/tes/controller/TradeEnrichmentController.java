package com.verygoodbank.tes.controller;

import com.verygoodbank.tes.model.Trade;
import com.verygoodbank.tes.service.TradeEnrichmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TradeEnrichmentController {

    @Autowired
    private TradeEnrichmentService tradeEnrichmentService;

    @PostMapping(value = "/enrich", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Trade> enrichTrades(@RequestParam("tradeFile") MultipartFile tradeFile) throws IOException {
        Path productsFilePath = Path.of("src/main/resources/products.csv");
        Path tradesFilePath = Path.of("src/main/resources/trades.csv");

        Files.write(tradesFilePath, tradeFile.getBytes());

        return tradeEnrichmentService.enrichTrades(productsFilePath, tradesFilePath);
    }
}
