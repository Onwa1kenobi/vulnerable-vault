CREATE DATABASE IF NOT EXISTS bankdb;

USE bankdb;

DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Accounts;
DROP TABLE IF EXISTS Transactions;
DROP TABLE IF EXISTS UserRoles;
  
-- Create the 'roles' table to define user roles
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY AUTO_INCREMENT,
    RoleName VARCHAR(50) NOT NULL UNIQUE
);

-- Insert predefined roles into the 'roles' table
INSERT INTO Roles (RoleName) VALUES ('Customer'), ('Teller'), ('Admin');

-- Create the 'users' table to store user details
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Email VARCHAR(100) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL, -- Passwords are stored as hashed values
    FullName VARCHAR(100) NOT NULL,
    RoleID INT,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

-- Create the 'accounts' table to store account details
CREATE TABLE Accounts (
    AccountID INT PRIMARY KEY AUTO_INCREMENT,
    AccountNumber VARCHAR(20) NOT NULL UNIQUE,
    Balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    CustomerID INT, -- Reference to the user who owns the account
    FOREIGN KEY (CustomerID) REFERENCES Users(UserID)
);

-- Create the 'transactions' table to store transaction details
CREATE TABLE Transactions (
    TransactionID INT PRIMARY KEY AUTO_INCREMENT,
    SourceAccountID INT, -- For transfer transactions, this is the account from which money is debited
    DestinationAccountID INT, -- For transfer transactions, this is the account to which money is credited
    TransactionDate DATETIME NOT NULL,
    TransactionType VARCHAR(20) NOT NULL, -- E.g., Deposit, Withdrawal, Transfer
    TransactionStatus VARCHAR(20) NOT NULL, -- E.g., Pending, Approved, Denied
    Amount DECIMAL(15, 2) NOT NULL,
    TransactionReceiptPath VARCHAR(250), -- File path for deposit receipt
    FOREIGN KEY (SourceAccountID) REFERENCES Accounts(AccountID),
    FOREIGN KEY (DestinationAccountID) REFERENCES Accounts(AccountID)
);

-- Create the 'user_roles' table to manage user-role relationships (if needed)
CREATE TABLE UserRoles (
    UserRoleID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    RoleID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

-- Example of how you might query to get all accounts for a specific customer
-- (assuming the query is executed by a customer logged in and their UserID is known)
-- SELECT * FROM Accounts WHERE CustomerID = [LoggedInCustomerID];
