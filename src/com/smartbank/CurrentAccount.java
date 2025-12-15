package com.smartbank;

public class CurrentAccount extends Account {

    public CurrentAccount(int accountNo, String name, double balance, double annualRate) {
        super(accountNo, name, balance, annualRate);
    }

    @Override
    public void monthEndProcess() {
        double fee = 10.0;
        balance = balance - fee;
    }
}
