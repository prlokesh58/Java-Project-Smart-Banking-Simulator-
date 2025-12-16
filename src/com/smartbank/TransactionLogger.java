package com.smartbank;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.io.File;

public class TransactionLogger {

    private static final String FILE_NAME = "transactions.csv";

    public static void log(Account acc, String type, double amount) {

        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {

            System.out.println("Writing to: " +
                    new File(FILE_NAME).getAbsolutePath());

            String line = LocalDateTime.now() + ","
                    + acc.getAccountNo() + ","
                    + acc.getName() + ","
                    + type + ","
                    + amount + ","
                    + acc.getBalance() + "\n";

            fw.write(line);
            fw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}