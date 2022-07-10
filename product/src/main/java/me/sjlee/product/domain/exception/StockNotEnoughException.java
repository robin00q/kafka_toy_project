package me.sjlee.product.domain.exception;

public class StockNotEnoughException extends IllegalStateException {

    public StockNotEnoughException(String s) {
        super(s);
    }
}
