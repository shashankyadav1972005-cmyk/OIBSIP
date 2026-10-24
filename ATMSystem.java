package atm;

import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private String userId;
    private String pin;
    private double balance;

    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited: ₹" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrawn: ₹" + amount);
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }
    }
}

public class ATMSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Default Login Credentials
        Account account = new Account("user123", "1234", 10000.0);
        ArrayList<String> transactionHistory = new ArrayList<>();

        System.out.println("=== Welcome to the ATM Interface ===");
        
        int attempts = 0;
        boolean isAuthenticated = false;

        // Login Authentication Loop (Max 3 attempts)
        while (attempts < 3) {
            System.out.print("Enter User ID: ");
            String inputId = scanner.next();
            System.out.print("Enter PIN: ");
            String inputPin = scanner.next();

            if (inputId.equals("user123") && account.validatePin(inputPin)) {
                isAuthenticated = true;
                System.out.println("Login Successful!\n");
                break;
            } else {
                attempts++;
                System.out.println("Invalid Credentials. Attempts left: " + (3 - attempts));
            }
        }

        if (!isAuthenticated) {
            System.out.println("Access Denied! Too many incorrect attempts.");
            scanner.close();
            return;
        }

        // ATM Main Menu
        int choice;
        do {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Check Balance");
            System.out.println("5. Quit");
            System.out.print("Choose an option (1-5): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Transaction History ---");
                    if (transactionHistory.isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        for (String t : transactionHistory) {
                            System.out.println(t);
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ₹");
                    double wAmount = scanner.nextDouble();
                    if (account.withdraw(wAmount)) {
                        transactionHistory.add("Withdrawn: ₹" + wAmount);
                    }
                    break;

                case 3:
                    System.out.print("Enter amount to deposit: ₹");
                    double dAmount = scanner.nextDouble();
                    account.deposit(dAmount);
                    transactionHistory.add("Deposited: ₹" + dAmount);
                    break;

                case 4:
                    System.out.println("Current Account Balance: ₹" + account.getBalance());
                    break;

                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option! Please choose between 1 and 5.");
            }
        } while (choice != 5);

        scanner.close();
    }
}