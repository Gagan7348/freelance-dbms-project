💼 Freelance Project Management System (DBMS)

📖 Project Overview
The Freelance Project Management System is a relational database solution developed using MySQL to efficiently manage interactions between Clients, Students (Freelancers), and Admins.
It models the complete lifecycle of a freelance project — from project posting to application submission, review, and final assignment — using a well-structured relational schema based on Enhanced Entity Relationship (EER) concepts.

🎯 Objectives
This project is designed with the following key objectives:
Design a normalized relational database (up to Third Normal Form – 3NF)
Enforce Entity Integrity and Referential Integrity
Model real-world freelance workflow processes
Implement and apply advanced SQL concepts
Optimize database performance using indexes and efficient queries

🏗 System Architecture
The system follows a 3-Layer Logical Architecture:

1️⃣ Presentation Layer
Handles all user interactions:
Clients post projects
Students apply for projects
Admin reviews and assigns projects

2️⃣ Business Logic Layer
Manages core system operations:
Application approval and rejection
Assignment creation
Project status tracking
Deadline monitoring

3️⃣ Data Layer (MySQL)
Responsible for data storage and processing:
Relational table design
Primary and Foreign key constraints
Indexing for performance
Views and advanced SQL queries

🔄 Workflow Model
Client → Project → Application → Admin Review → Assignment
This flow ensures a structured and controlled process from project creation to final allocation.

🧠 EER-Based Database Design
The database schema is derived from an Enhanced Entity Relationship (EER) Model, incorporating:
Strong entities
1:1 and 1:N relationships
Cardinality constraints
Participation constraints
Strict referential integrity enforcement

🔗 Relationship Summary
One Client → Many Projects (1:N)
One Project → Many Applications (1:N)
One Student → Many Applications (1:N)
One Project → One Assignment (1:1)
One Admin → Many Assignments (1:N)
One Category → Many Projects (1:N)

🗂 Database Tables
📌 CLIENT
Stores details of clients who post projects.
📌 STUDENT
Maintains freelancer information, including skills.
📌 PROJECT
Contains project-related details:
Title
Budget
Deadline
Category
📌 APPLICATION
Tracks applications submitted by students for projects.
📌 ASSIGNMENT
Represents final project allocation after admin approval.
📌 CATEGORY
Classifies projects into various domains.
📌 ADMIN
Handles application review and project assignment.

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
10+ complex queries
INNER JOIN and LEFT JOIN
GROUP BY and HAVING
Aggregate functions:
COUNT()
SUM()
AVG()
MAX()
Subqueries
✅ Optimization
Indexing for faster query execution
Schema normalization up to 3NF

🛠 Technologies Used
MySQL Server
MySQL Workbench
SQL (DDL, DML, DQL)
GitHub

🎓 Academic Significance
This project effectively demonstrates:
EER to Relational Model Mapping
Database Normalization (up to 3NF)
Constraint Enforcement
Real-world Workflow Modeling
Query Optimization Techniques
Practical implementation of core DBMS concepts
