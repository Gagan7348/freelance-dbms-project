-- Database: freelance_db
-- Create Tables for Freelance Project Allocation System

CREATE DATABASE IF NOT EXISTS freelance_db;
USE freelance_db;

-- Table: Users (Roles: ADMIN, CLIENT, STUDENT)
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role ENUM('ADMIN', 'CLIENT', 'STUDENT') NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Table: Projects
CREATE TABLE IF NOT EXISTS projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    budget DECIMAL(10, 2),
    deadline DATE,
    client_id INT,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'ASSIGNED', 'COMPLETED') DEFAULT 'PENDING',
    rejection_reason TEXT,
    FOREIGN KEY (client_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Table: Applications (Students applying for projects)
CREATE TABLE IF NOT EXISTS applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT,
    student_id INT,
    proposal TEXT,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Table: Assignments (Project assigned to 1 Student)
CREATE TABLE IF NOT EXISTS assignments (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT UNIQUE,
    student_id INT,
    assigned_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completion_date TIMESTAMP NULL,
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Pre-fill with Admin
INSERT IGNORE INTO users (username, password, role, email) 
VALUES ('admin', 'admin123', 'ADMIN', 'admin@freelance.com');
