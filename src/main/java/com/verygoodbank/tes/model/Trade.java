package com.verygoodbank.tes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Trade {
    @JsonProperty("date")
    private String date;

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("price")
    private double price;

    private String productName;


    public Trade(String date, int productId, String currency, double price) {
        this.date = date;
        this.productId = productId;
        this.currency = currency;
        this.price = price;
    }


    public String getDate() {
        return date;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}