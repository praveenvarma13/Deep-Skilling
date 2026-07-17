CREATE DATABASE IF NOT EXISTS springrestlearn;
USE springrestlearn;

-- Create Country Table
CREATE TABLE IF NOT EXISTS country (
    co_code VARCHAR(5) PRIMARY KEY,
    co_name VARCHAR(50) NOT NULL
);

-- Seed Initial Records
INSERT INTO country (co_code, co_name) VALUES 
('IN', 'India'),
('US', 'United States'),
('DE', 'Germany'),
('JP', 'Japan')
ON DUPLICATE KEY UPDATE co_name=co_name;