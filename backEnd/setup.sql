DROP DATABASE IF EXISTS CSCI201_Final_Project;
CREATE DATABASE CSCI201_Final_Project;
USE CSCI201_Final_Project;

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE JobApplications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    company VARCHAR(100) NOT NULL,
    job_position VARCHAR(100) NOT NULL,
    job_description TEXT,
    application_deadline DATE,
    application_requirements TEXT,
    additional_info TEXT,
    time_applied TEXT,
    application_status VARCHAR(50),
    user_id INT,

    FOREIGN KEY (user_id) REFERENCES Users(user_id)
        ON DELETE CASCADE
);

-- ■ INSERT SAMPLE USERS
INSERT INTO Users (username, password)
VALUES
  ('alice_wang',   'alicePass!23'),
  ('bob_johnson',  'b0bSecure456');

-- ■ INSERT SAMPLE JOB APPLICATIONS
INSERT INTO JobApplications (
    company,
    job_position,
    job_description,
    application_deadline,
    application_requirements,
    additional_info,
    time_applied,
    application_status,
    user_id
)
VALUES
  (
    'Duolingo',
    'Data Scientist Intern',
    'Develop ML models to personalize language lessons.',
    '2025-06-30',
    'Python, SQL, TensorFlow experience',
    'Referred by Prof. Smith',
    '2025-04-28 14:15:00',
    'Applied',
    1
  ),
  (
    'Huaqin Technology',
    'Embedded Software Engineer',
    'Optimize firmware for true-wireless earbuds.',
    '2025-05-15',
    'C++, RTOS, Bluetooth protocol',
    'On-site internship position',
    '2025-04-20 09:30:00',
    'Interview Scheduled',
    2
  ),
  (
    'Google',
    'Student Researcher',
    'Assist with data analysis for safety analytics platform.',
    '2025-07-01',
    'Java, MongoDB, data viz skills',
    'USC Robotic Embedded Systems Lab',
    '2025-04-25 11:45:00',
    'Offer Received',
    1
  );