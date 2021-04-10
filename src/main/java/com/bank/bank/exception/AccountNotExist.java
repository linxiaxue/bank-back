package com.bank.bank.exception;

public class AccountNotExist extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;

    public AccountNotExist() {
        super("账号不存在");
    }
}
