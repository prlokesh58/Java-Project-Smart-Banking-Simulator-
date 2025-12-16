package com.smartbank;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SmartBankApp 
{
    static Account[] accounts = new Account[7];
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
    	accounts[0] = new SavingsAccount(1, "Ali", 1000, 6.0);
        accounts[1] = new CurrentAccount(2, "Hemanth", 1500, 0.0);
        accounts[2] = new SavingsAccount(3, "Vishal", 2000, 5.0);
        accounts[3] = new SavingsAccount(4, "Ramu", 5000, 3.0);
        accounts[4] = new SavingsAccount(5, "Balu", 2500, 5.0);
        accounts[5] = new SavingsAccount(6, "Gopi", 1250, 2.0);
        accounts[6] = new SavingsAccount(7, "Chandu", 3000, 7.0);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1: doDeposit(); break;
                case 2: doWithdraw(); break;
                case 3: doTransfer(); break;
                case 4: interestCalculator(); break;
                case 5: runMonthEndForAll(); break;
                case 6: showAllAccounts(); break;
                case 7: showAllTransactions(); break;
                case 0:
                    System.out.println("Exiting... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void printMenu() {
        System.out.println("\n===== SMART BANK MENU =====");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Interest Calculator");
        System.out.println("5. Run Month-End Processing");
        System.out.println("6. Show All Accounts");
        System.out.println("7. Show Transaction History");
        System.out.println("0. Exit");
    }

    static int readInt(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter valid integer: ");
        }
        return sc.nextInt();
    }

    static double readDouble(String msg) {
        System.out.print(msg);
        while (!sc.hasNextDouble()) {
            sc.next();
            System.out.print("Enter valid number: ");
        }
        return sc.nextDouble();
    }

    static Account findAccount(int no) {
        for (Account a : accounts) {
            if (a != null && a.getAccountNo() == no)
                return a;
        }
        return null;
    }

    static void doDeposit() {
        Account acc = findAccount(readInt("Account number: "));
        if (acc == null) return;

        double amt = readDouble("Amount: ");
        acc.deposit(amt);
        TransactionLogger.log(acc, "DEPOSIT", amt);
        System.out.println("Balance: " + acc.getBalance());
    }

    static void doWithdraw() {
        Account acc = findAccount(readInt("Account number: "));
        if (acc == null) return;

        double amt = readDouble("Amount: ");
        acc.withdraw(amt);
        TransactionLogger.log(acc, "WITHDRAW", amt);
        System.out.println("Balance: " + acc.getBalance());
    }

    static void doTransfer() {
        Account from = findAccount(readInt("From account: "));
        Account to = findAccount(readInt("To account: "));
        if (from == null || to == null) return;

        double amt = readDouble("Amount: ");

        from.withdraw(amt);
        to.deposit(amt);

        TransactionLogger.log(from, "TRANSFER_OUT", amt);
        TransactionLogger.log(to, "TRANSFER_IN", amt);

        System.out.println("Transfer successful.");
    }

    static void interestCalculator() {
        double P = readDouble("Principal: ");
        double R = readDouble("Rate (%): ");
        double T = readDouble("Years: ");

        double simple = (P * R * T) / 100;
        double compound = P * Math.pow(1 + R / 100, T) - P;

        System.out.println("Simple Interest: " + simple);
        System.out.println("Compound Interest: " + compound);
    }

    static void runMonthEndForAll() {
        for (Account a : accounts)
            if (a != null)
                a.monthEndProcess();
        System.out.println("Month-end completed.");
    }

    static void showAllAccounts() {
        for (Account a : accounts)
            if (a != null)
                System.out.println(a);
    }

    // ⭐ TRANSACTION HISTORY
    static void showAllTransactions() {
        System.out.println("\n=== TRANSACTION HISTORY ===");

        try (BufferedReader br = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No transactions found.");
        }
    }
}