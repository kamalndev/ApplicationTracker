DROP DATABASE IF EXISTS CSCI201_Final_Project;
CREATE CSCI201_Final_Project;
USE CSCI201_Final_Project;

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
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

INSERT INTO Users (username, password);
VALUES ('cliaredang', 'securepassword123');
