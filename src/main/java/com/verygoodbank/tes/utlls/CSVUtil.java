package com.verygoodbank.tes.utlls;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.verygoodbank.tes.model.Trade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class CSVUtil {

    public static Map<Integer, String> readProducts(Path filePath) throws IOException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);

        ProductsRowProcessor processor = new ProductsRowProcessor();
        settings.setProcessor(processor);

        CsvParser parser = new CsvParser(settings);
        parser.parse(Files.newBufferedReader(filePath));

        return processor.getProducts();
    }

    public static List<Trade> readTrades(Path filePath) throws IOException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);

        TradesRowProcessor processor = new TradesRowProcessor();
        settings.setProcessor(processor);

        CsvParser parser = new CsvParser(settings);
        parser.parse(Files.newBufferedReader(filePath));

        return processor.getTrades();
    }
}

