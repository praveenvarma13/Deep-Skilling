package com.cognizant.banking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id @Column(name = "account_number") private String accountNumber;
    @Column(name = "customer_name") private String customerName;
    private double balance;
    @Column(name = "account_type") private String accountType;

    public BankAccount() {}
    public String getAccountNumber() { return accountNumber; }
    public String getCustomerName() { return customerName; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
}