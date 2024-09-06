package com.verygoodbank.tes.utlls;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.AbstractRowProcessor;
import com.verygoodbank.tes.model.Trade;

import java.util.ArrayList;
import java.util.List;

class TradesRowProcessor extends AbstractRowProcessor {
    private final List<Trade> trades = new ArrayList<>();

    @Override
    public void rowProcessed(String[] row, ParsingContext context) {
        String date = row[0];
        int productId = Integer.parseInt(row[1]);
        String currency = row[2];
        double price = Double.parseDouble(row[3]);
        trades.add(new Trade(date, productId, currency, price));
    }

    public List<Trade> getTrades() {
        return trades;
    }
}
