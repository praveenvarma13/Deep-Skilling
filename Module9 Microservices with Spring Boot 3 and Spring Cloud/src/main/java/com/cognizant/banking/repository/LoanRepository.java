package com.cognizant.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cognizant.banking.model.BankLoan;

public interface LoanRepository extends JpaRepository<BankLoan, String> {
}