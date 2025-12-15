package com.smartbank;

public abstract class Account implements BankOperations {

    protected int accountNo;
    protected String name;
    protected double balance;
    protected double annualRate;

    public Account(int accountNo, String name, double balance, double annualRate) {
        this.accountNo = accountNo;
        this.name = name;
        this.balance = balance;
        this.annualRate = annualRate;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    // Interface methods – handle exceptions inside
    @Override
    public void deposit(double amount) {
        try {
            doDeposit(amount);
        } catch (NegativeAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        try {
            doWithdraw(amount);
        } catch (NegativeAmountException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Real logic with checked exceptions
    public void doDeposit(double amount) throws NegativeAmountException {
        if (amount <= 0) {
            throw new NegativeAmountException("Amount must be greater than 0.");
        }
        balance = balance + amount;
    }

    public void doWithdraw(double amount) throws NegativeAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new NegativeAmountException("Amount must be greater than 0.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Not enough balance.");
        }
        balance = balance - amount;
    }

    public abstract void monthEndProcess();

    @Override
    public String toString() {
        return "AccountNo: " + accountNo + ", Name: " + name + ", Balance: " + balance;
    }
}
