CREATE DATABASE IF NOT EXISTS central_bank_db;
USE central_bank_db;

-- Account Table Storage
CREATE TABLE IF NOT EXISTS bank_account (
    account_number VARCHAR(20) PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    balance DOUBLE NOT NULL,
    account_type VARCHAR(20) NOT NULL
);

-- Loan Table Storage
CREATE TABLE IF NOT EXISTS bank_loan (
    loan_id VARCHAR(20) PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    loan_amount DOUBLE NOT NULL,
    loan_type VARCHAR(20) NOT NULL
);

-- Seed Account Data
INSERT INTO bank_account (account_number, customer_name, balance, account_type) VALUES 
('ACC1001', 'John Doe', 45000.00, 'Savings'),
('ACC1002', 'Jane Smith', 89000.50, 'Current')
ON DUPLICATE KEY UPDATE balance=VALUES(balance);

-- Seed Loan Data
INSERT INTO bank_loan (loan_id, customer_name, loan_amount, loan_type) VALUES 
('LN9001', 'Alice Johnson', 250000.00, 'Home Loan'),
('LN9002', 'Bob Martin', 15000.00, 'Personal')
ON DUPLICATE KEY UPDATE loan_amount=VALUES(loan_amount);