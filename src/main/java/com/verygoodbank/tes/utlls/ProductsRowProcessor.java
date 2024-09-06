package com.verygoodbank.tes.utlls;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.AbstractRowProcessor;

import java.util.HashMap;
import java.util.Map;

class ProductsRowProcessor extends AbstractRowProcessor {
    private final Map<Integer, String> products = new HashMap<>();

    @Override
    public void rowProcessed(String[] row, ParsingContext context) {
        int productId = Integer.parseInt(row[0]);
        String productName = row[1];
        products.put(productId, productName);
    }

    public Map<Integer, String> getProducts() {
        return products;
    }
}
