CREATE DATABASE IF NOT EXISTS spring_employee_db;
USE spring_employee_db;

-- 1. Create Department Table
CREATE TABLE IF NOT EXISTS department (
    dp_id INT PRIMARY KEY AUTO_INCREMENT,
    dp_name VARCHAR(50) NOT NULL
);

-- 2. Create Employee Table
CREATE TABLE IF NOT EXISTS employee (
    em_id INT PRIMARY KEY AUTO_INCREMENT,
    em_name VARCHAR(100) NOT NULL,
    em_salary DOUBLE NOT NULL,
    em_permanent BOOLEAN NOT NULL,
    em_dp_id INT,
    FOREIGN KEY (em_dp_id) REFERENCES department(dp_id)
);

-- Seed Initial Departments
INSERT INTO department (dp_id, dp_name) VALUES 
(1, 'IT'), 
(2, 'HR') 
ON DUPLICATE KEY UPDATE dp_name=VALUES(dp_name);

-- Seed Initial Employees
INSERT INTO employee (em_id, em_name, em_salary, em_permanent, em_dp_id) VALUES 
(1, 'John Doe', 75000.0, TRUE, 1),
(2, 'Jane Smith', 62000.0, FALSE, 2),
(3, 'Alice Johnson', 85000.0, TRUE, 1)
ON DUPLICATE KEY UPDATE em_name=VALUES(em_name);