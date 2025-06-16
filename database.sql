CREATE USER 'dealership_app'@'localhost' IDENTIFIED BY 'StrongP@ssw0rd';
GRANT ALL PRIVILEGES ON car_dealership.* TO 'dealership_app'@'localhost';
FLUSH PRIVILEGES;
DROP DATABASE IF EXISTS car_dealership;
CREATE DATABASE car_dealership;
USE car_dealership;

CREATE TABLE dealerships(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    address VARCHAR(100),
    phone VARCHAR(20)
);

CREATE TABLE vehicles(
    id INT PRIMARY KEY AUTO_INCREMENT,
    vin VARCHAR(17) UNIQUE,
    make VARCHAR(50),
    model VARCHAR(50),
    year INT,
    price DECIMAL(10,2),
    color VARCHAR(30),
    mileage INT,
    type VARCHAR(30)
);

CREATE TABLE sales_contracts(
    id INT PRIMARY KEY AUTO_INCREMENT,
    vin VARCHAR(17),
    customer VARCHAR(100),
    amount DECIMAL(10,2),
    date DATE
);

CREATE TABLE lease_contracts(
    id INT PRIMARY KEY AUTO_INCREMENT,
    vin VARCHAR(17),
    customer VARCHAR(100),
    amount DECIMAL(10,2),
    date DATE
);

INSERT INTO dealerships(name,address,phone) VALUES
('North Jersey Autos','123 Main St, Paramus NJ','201-555-1234'),
('Garden State Motors','45 Route 17, Ramsey NJ','201-555-4567');

INSERT INTO vehicles(vin,make,model,year,price,color,mileage,type) VALUES
('1HGCM82633A004352','Honda','Accord',2021,21995.0,'Blue',15000,'Sedan'),
('1N4AL3AP0JC123456','Nissan','Altima',2020,18450.0,'Silver',23000,'Sedan');

INSERT INTO sales_contracts(vin,customer,amount,date) VALUES
('1HGCM82633A004352','Alice Apple',21500.00,'2025-06-01');
