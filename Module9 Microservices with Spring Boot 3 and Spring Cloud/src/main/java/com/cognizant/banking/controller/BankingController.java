package com.cognizant.banking.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.banking.model.BankAccount;
import com.cognizant.banking.model.BankLoan;
import com.cognizant.banking.repository.AccountRepository;
import com.cognizant.banking.repository.LoanRepository;

@RestController
public class BankingController {

    @Autowired private AccountRepository accountRepository;
    @Autowired private LoanRepository loanRepository;

    // Account Microservice Domain Endpoint Output
    @GetMapping(value = "/accounts/details", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getAccountMicroserviceData() {
        List<BankAccount> list = accountRepository.findAll();
        StringBuilder sb = new StringBuilder("=== ACCOUNT MICROSERVICE DATA (PORT: 8081) ===\n");
        sb.append(String.format("%-12s | %-15s | %-12s | %-12s\n", "ACC NO", "NAME", "BALANCE", "TYPE"));
        sb.append("-----------------------------------------------------------\n");
        for (BankAccount acc : list) {
            sb.append(String.format("%-12s | %-15s | %-12.2f | %-12s\n", acc.getAccountNumber(), acc.getCustomerName(), acc.getBalance(), acc.getAccountType()));
        }
        return sb.toString();
    }

    // Loan Microservice Domain Endpoint Output
    @GetMapping(value = "/loans/details", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getLoanMicroserviceData() {
        List<BankLoan> list = loanRepository.findAll();
        StringBuilder sb = new StringBuilder("=== LOAN MICROSERVICE DATA (PORT: 8082) ===\n");
        sb.append(String.format("%-12s | %-15s | %-12s | %-12s\n", "LOAN ID", "NAME", "AMOUNT", "TYPE"));
        sb.append("-----------------------------------------------------------\n");
        for (BankLoan loan : list) {
            sb.append(String.format("%-12s | %-15s | %-12.2f | %-12s\n", loan.getLoanId(), loan.getCustomerName(), loan.getLoanAmount(), loan.getLoanType()));
        }
        return sb.toString();
    }
}