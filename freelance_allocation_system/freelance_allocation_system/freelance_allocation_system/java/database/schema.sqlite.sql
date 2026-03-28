-- SQLite Database schema for Freelance System
-- Tables will be created automatically by the Java code if they don't exist.

-- Table: users
CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT CHECK(role IN ('ADMIN', 'CLIENT', 'STUDENT')) NOT NULL,
    email TEXT UNIQUE NOT NULL
);

-- Table: projects
CREATE TABLE IF NOT EXISTS projects (
    project_id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    description TEXT,
    budget REAL,
    deadline TEXT,
    client_id INTEGER,
    status TEXT DEFAULT 'PENDING' CHECK(status IN ('PENDING', 'APPROVED', 'REJECTED', 'ASSIGNED', 'COMPLETED')),
    rejection_reason TEXT,
    FOREIGN KEY (client_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Table: applications
CREATE TABLE IF NOT EXISTS applications (
    application_id INTEGER PRIMARY KEY AUTOINCREMENT,
    project_id INTEGER,
    student_id INTEGER,
    proposal TEXT,
    status TEXT DEFAULT 'PENDING' CHECK(status IN ('PENDING', 'APPROVED', 'REJECTED')),
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Table: assignments
CREATE TABLE IF NOT EXISTS assignments (
    assignment_id INTEGER PRIMARY KEY AUTOINCREMENT,
    project_id INTEGER UNIQUE,
    student_id INTEGER,
    assigned_date TEXT DEFAULT CURRENT_TIMESTAMP,
    completion_date TEXT,
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Pre-fill with Admin (using INSERT OR IGNORE)
INSERT OR IGNORE INTO users (username, password, role, email) 
VALUES ('admin', 'admin123', 'ADMIN', 'admin@freelance.com');
