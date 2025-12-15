package com.smartbank;

public class SavingsAccount extends Account {

    public SavingsAccount(int accountNo, String name, double balance, double annualRate) {
        super(accountNo, name, balance, annualRate);
    }

    @Override
    public void monthEndProcess() {
        double monthlyRate = annualRate / 12.0 / 100.0;
        double interest = balance * monthlyRate;
        balance = balance + interest;
    }
}
