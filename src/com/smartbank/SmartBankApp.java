package com.smartbank;
import java.util.Scanner; 
/*
 * SmartBankApp (simple, one package)
 * ----------------------------------
 * - One package: com.smartbank
 * - Classes in same package:
 *      Account (abstract)
 *      SavingsAccount, CurrentAccount
 *      BankOperations (interface)
 *      InsufficientFundsException, NegativeAmountException
 * - Uses simple array of accounts (no Map / HashMap)
 * - Menu with switch-case
 * - Simple & compound interest
 */

public class SmartBankApp {

    static Account[] accounts = new Account[3];
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) 
    {
        // Demo accounts
        accounts[0] = new SavingsAccount(1, "Alice", 1000, 6.0);
        accounts[1] = new CurrentAccount(2, "Bob", 1500, 0.0);
        accounts[2] = new SavingsAccount(3, "Charlie", 2000, 5.0);

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    doDeposit();
                    break;
                case 2:
                    doWithdraw();
                    break;
                case 3:
                    doTransfer();
                    break;
                case 4:
                    interestCalculator();
                    break;
                case 5:
                    runMonthEndForAll();
                    break;
                case 6:
                    showAllAccounts();
                    break;
                case 0:
                    System.out.println("Exiting... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // ===== Menu and input helpers =====

    static void printMenu() {
        System.out.println();
        System.out.println("===== SMART BANK MENU =====");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Interest Calculator (Simple + Compound)");
        System.out.println("5. Run Month-End Processing");
        System.out.println("6. Show All Accounts");
        System.out.println("0. Exit");
    }

    static int readInt(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid integer: ");
            sc.next();
        }
        return sc.nextInt();
    }

    static double readDouble(String msg) {
        System.out.print(msg);
        while (!sc.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextDouble();
    }

    // ===== Account helper =====

    static Account findAccount(int accNo) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getAccountNo() == accNo) {
                return accounts[i];
            }
        }
        return null;
    }

    // ===== Operations =====

    static void doDeposit() {
        int no = readInt("Enter account number: ");
        Account acc = findAccount(no);

        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        double amount = readDouble("Enter amount to deposit: ");
        acc.deposit(amount); // exceptions handled inside
        System.out.println("Current balance: " + acc.getBalance());
    }

    static void doWithdraw() {
        int no = readInt("Enter account number: ");
        Account acc = findAccount(no);

        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        double amount = readDouble("Enter amount to withdraw: ");
        acc.withdraw(amount); // exceptions handled inside
        System.out.println("Current balance: " + acc.getBalance());
    }

    static void doTransfer() {
        System.out.println("From account:");
        int fromNo = readInt("Enter account number: ");
        Account from = findAccount(fromNo);
        if (from == null) {
            System.out.println("From account not found.");
            return;
        }

        System.out.println("To account:");
        int toNo = readInt("Enter account number: ");
        Account to = findAccount(toNo);
        if (to == null) {
            System.out.println("To account not found.");
            return;
        }

        double amount = readDouble("Enter amount to transfer: ");

        try {
            from.doWithdraw(amount);
            to.doDeposit(amount);
            System.out.println("Transfer successful.");
        } catch (NegativeAmountException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("From balance: " + from.getBalance());
        System.out.println("To balance: " + to.getBalance());
    }

    // Simple + Compound interest
    static void interestCalculator() {
        System.out.println("=== Interest Calculator ===");
        double P = readDouble("Enter principal (P): ");
        double R = readDouble("Enter annual rate in % (R): ");
        double T = readDouble("Enter time in years (T): ");

        double simple = (P * R * T) / 100.0;                  // simple interest [web:11][web:35]
        double A = P * Math.pow(1 + R / 100.0, T);            // amount with compounding [web:11][web:35]
        double compound = A - P;

        System.out.println("Simple Interest = " + simple);
        System.out.println("Compound Interest = " + compound);
    }

    static void runMonthEndForAll() {
        System.out.println("Running month-end processing for all accounts...");
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null) {
                accounts[i].monthEndProcess();
            }
        }
        System.out.println("Month-end processing completed.");
    }

    static void showAllAccounts() {
        System.out.println("=== Account List ===");
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null) {
                System.out.println(accounts[i]);
            }
        }
    }
}
