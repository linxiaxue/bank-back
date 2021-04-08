package com.bank.bank.exception;


public class BalanceNotEnoughException extends RuntimeException {

    private static final long serialVersionUID = -6074753940710869977L;

    public BalanceNotEnoughException() {
        super("余额不止");
    }
}