💼 Freelance Project Management System (DBMS)
📖 Project Overview

The Freelance Project Management System is a relational database project developed using MySQL to manage freelance operations between Clients, Students (Freelancers), and Admin.

The system models the complete lifecycle of a freelance project — from project posting to application review and final assignment — using structured relational schema design and Enhanced Entity Relationship (EER) concepts.

🎯 Objective

The objectives of this project are to:

Design a normalized relational database (up to Third Normal Form – 3NF)

Implement Entity Integrity and Referential Integrity constraints

Model real-world freelance workflow processes

Apply advanced SQL concepts

Optimize queries using indexes and joins

🏗 System Architecture

The system follows a 3-Layer Logical Architecture:

1️⃣ Presentation Layer

Handles user interactions:

Client posts projects

Student applies for projects

Admin reviews and assigns projects

2️⃣ Business Logic Layer

Controls workflow operations:

Application approval/rejection

Assignment creation

Status tracking

Deadline management

3️⃣ Data Layer (MySQL)

Implements:

Relational tables

Primary & Foreign key constraints

Indexes

Views

Advanced SQL queries

🔄 Workflow Model
Client → Project → Application → Admin Review → Assignment
🧠 EER-Based Database Design

The database schema is derived from an Enhanced Entity Relationship (EER) Model, including:

Strong entities

1:1 and 1:N relationships

Cardinality constraints

Participation constraints

Referential integrity enforcement

🔗 Relationship Summary

One Client → Many Projects (1:N)

One Project → Many Applications (1:N)

One Student → Many Applications (1:N)

One Project → One Assignment (1:1)

One Admin → Many Assignments (1:N)

One Category → Many Projects (1:N)

🗂 Database Tables
📌 CLIENT

Stores client details who post projects.

📌 STUDENT

Stores freelancer details and skills.

📌 PROJECT

Contains project information including:

Title

Budget

Deadline

Category

📌 APPLICATION

Tracks student applications for projects.

📌 ASSIGNMENT

Represents final allocation of project after admin approval.

📌 CATEGORY

Classifies projects into different domains.

📌 ADMIN

Manages application review and project assignment.

🔍 Features Implemented
✅ Data Definition (DDL)

CREATE TABLE with constraints

Primary Keys & Foreign Keys

NOT NULL and UNIQUE constraints

✅ Data Manipulation (DML)

INSERT

UPDATE

DELETE

✅ Advanced SQL

6 SQL Views

10+ Advanced Queries

INNER JOIN & LEFT JOIN

GROUP BY & HAVING

Aggregate Functions:

COUNT()

SUM()

AVG()

MAX()

Subqueries

✅ Optimization

Indexes for performance improvement

Schema normalized up to 3NF

🛠 Technologies Used

MySQL Server

MySQL Workbench

SQL (DDL, DML, DQL)

GitHub

▶ Setup Instructions
CREATE DATABASE freelance_db;
USE freelance_db;

Execute table creation scripts

Insert sample records

Run queries and views

🎓 Academic Significance

This project demonstrates:

EER to Relational Mapping

Database Normalization (up to 3NF)

Constraint Enforcement

Workflow Modeling

Query Optimization

Practical Implementation of Core DBMS Concepts
