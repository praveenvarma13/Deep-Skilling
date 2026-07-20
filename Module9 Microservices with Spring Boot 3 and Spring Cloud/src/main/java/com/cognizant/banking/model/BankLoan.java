package com.cognizant.banking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_loan")
public class BankLoan {
    @Id @Column(name = "loan_id") private String loanId;
    @Column(name = "customer_name") private String customerName;
    @Column(name = "loan_amount") private double loanAmount;
    @Column(name = "loan_type") private String loanType;

    public BankLoan() {}
    public String getLoanId() { return loanId; }
    public String getCustomerName() { return customerName; }
    public double getLoanAmount() { return loanAmount; }
    public String getLoanType() { return loanType; }
}