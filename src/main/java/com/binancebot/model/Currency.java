package com.binancebot.model;

public class Currency {

    private String symbol;

    private double price;

    public Currency(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public Currency() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
