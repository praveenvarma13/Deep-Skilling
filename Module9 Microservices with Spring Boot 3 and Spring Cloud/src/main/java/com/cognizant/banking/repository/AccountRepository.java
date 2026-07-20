package com.cognizant.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cognizant.banking.model.BankAccount;

public interface AccountRepository extends JpaRepository<BankAccount, String> {
}