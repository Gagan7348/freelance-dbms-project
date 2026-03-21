INSERT INTO CATEGORY (Category_Name) VALUES
('Web Development'),
('App Development'),
('Machine Learning'),
('UI/UX Design'),
('Data Science'),
('Cyber Security'),
('Cloud Computing'),
('Game Development');

INSERT INTO ADMIN (Name, Email, Password)
VALUES ('System Admin', 'admin@freelance.com', SHA2('admin123',256));

INSERT INTO CLIENT (Name, Email, Password, Company_Name) VALUES
('Raj Sharma', 'raj@gmail.com', SHA2('raj123',256), 'TechCorp'),
('Anita Verma', 'anita@gmail.com', SHA2('anita123',256), 'DesignHub'),
('Rohit Mehta', 'rohit@gmail.com', SHA2('rohit123',256), 'DataWorks'),
('Karan Singh', 'karan@gmail.com', SHA2('karan123',256), 'AppStudio'),
('Sneha Kapoor', 'sneha@gmail.com', SHA2('sneha123',256), 'CloudNet');

INSERT INTO STUDENT (Name, Email, Password, Skills, CGPA) VALUES
('Diya Yadav', 'diya@gmail.com', SHA2('diya123',256), 'Python, SQL', 8.5),
('Aman Gupta', 'aman@gmail.com', SHA2('aman123',256), 'Java, Spring', 7.9),
('Priya Sharma', 'priya@gmail.com', SHA2('priya123',256), 'UI/UX, Figma', 8.2),
('Rahul Verma', 'rahul@gmail.com', SHA2('rahul123',256), 'Machine Learning', 8.8),
('Neha Singh', 'neha@gmail.com', SHA2('neha123',256), 'React, Node', 7.5),
('Arjun Patel', 'arjun@gmail.com', SHA2('arjun123',256), 'Cyber Security', 8.1),
('Meera Joshi', 'meera@gmail.com', SHA2('meera123',256), 'Cloud AWS', 7.8),
('Vikram Rao', 'vikram@gmail.com', SHA2('vikram123',256), 'Game Dev Unity', 8.0);

INSERT INTO PROJECT (Title, Description, Budget, Deadline, Client_ID) VALUES
('E-Commerce Website', 'Build online shopping site', 20000, '2026-06-01', 1),
('Mobile Banking App', 'Android banking app', 30000, '2026-07-15', 4),
('AI Chatbot', 'Customer support bot', 15000, '2026-05-20', 3),
('Portfolio Website', 'Personal portfolio site', 8000, '2026-04-10', 2),
('Cloud Migration', 'Move system to AWS', 25000, '2026-08-01', 5),
('ML Prediction Model', 'Sales forecasting model', 18000, '2026-06-25', 3),
('Game App', '2D Android game', 12000, '2026-05-05', 4),
('Cyber Audit', 'Security testing', 22000, '2026-07-01', 1),
('Dashboard UI', 'Analytics dashboard UI', 10000, '2026-04-30', 2),
('Data Cleaning Tool', 'Python data tool', 9000, '2026-05-15', 3);

INSERT INTO APPLICATION (Student_ID, Project_ID) VALUES
(1,1),(2,1),(3,4),(4,3),(5,1),
(6,8),(7,5),(8,7),(1,6),(2,2),
(3,9),(4,6),(5,2),(6,3),(7,10);

SELECT 'CLIENT' AS tbl, COUNT(*) FROM CLIENT
UNION
SELECT 'STUDENT', COUNT(*) FROM STUDENT
UNION
SELECT 'PROJECT', COUNT(*) FROM PROJECT
UNION
SELECT 'APPLICATION', COUNT(*) FROM APPLICATION;