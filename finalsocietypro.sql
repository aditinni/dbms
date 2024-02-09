CREATE DATABASE finalsocietypro;
USE finalsocietypro;
CREATE TABLE IF NOT EXISTS Blocks (
    block CHAR(1) PRIMARY KEY
);
INSERT INTO Blocks (block) VALUES ('A'), ('B'), ('C'), ('D');

CREATE TABLE IF NOT EXISTS Residents_s (
    resident_id INT PRIMARY KEY,
    resident_name VARCHAR(100) NOT NULL,
    flat_number VARCHAR(20) NOT NULL,
    block CHAR(1),
    FOREIGN KEY (block) REFERENCES Blocks(block)
);

INSERT INTO Residents_s (resident_id, resident_name, flat_number, block) VALUES
(1, 'John Doe', '101', 'A'),
(2, 'Jane Smith', '102', 'B'),
(3, 'Michael Johnson', '103', 'C'),
(4, 'Emily Davis', '104', 'D'),
(5, 'David Brown', '105', 'A'),
(6, 'Emma Wilson', '106', 'B'),
(7, 'James Martinez', '107', 'C'),
(8, 'Olivia Anderson', '108', 'D'),
(9, 'William Taylor', '109', 'A'),
(10, 'Sophia Thomas', '110', 'B');

CREATE TABLE IF NOT EXISTS ClubStable (
    club_name VARCHAR(100) PRIMARY KEY,
    block CHAR(1),
    FOREIGN KEY (block) REFERENCES Blocks(block)
);

INSERT INTO ClubStable (club_name, block) VALUES
('Tennis Club', 'A'),
('Fitness Club', 'B'),
('Swimming Club', 'C'),
('Chess Club', 'D'),
('Yoga Club', 'A'),
('Music Club', 'B'),
('Dance Club', 'C'),
('Reading Club', 'D'),
('Gardening Club', 'A'),
('Cooking Club', 'B');

CREATE TABLE IF NOT EXISTS Complaints_s (
    complaint_id INT AUTO_INCREMENT PRIMARY KEY,
    resident_id INT,
    resident_name VARCHAR(100) NOT NULL,
    block CHAR(1),
    complain_text TEXT,
    FOREIGN KEY (resident_id) REFERENCES Residents_s(resident_id),
    FOREIGN KEY (block) REFERENCES Blocks(block)
);

INSERT INTO Complaints_s (resident_id, resident_name, block, complain_text) VALUES
(1, 'John Doe', 'A', 'Noise complaint in the common area'),
(2, 'Jane Smith', 'B', 'Maintenance issue in the elevator'),
(3, 'Michael Johnson', 'C', 'Garbage not being collected regularly'),
(4, 'Emily Davis', 'D', 'Loud music from neighbor late at night'),
(5, 'David Brown', 'A', 'Parking issue in the society parking lot'),
(6, 'Emma Wilson', 'B', 'Water leakage in the bathroom'),
(7, 'James Martinez', 'C', 'Broken streetlights in the society'),
(8, 'Olivia Anderson', 'D', 'Unauthorized entry into the society premises'),
(9, 'William Taylor', 'A', 'Dog barking nuisance in the society'),
(10, 'Sophia Thomas', 'B', 'Lack of cleanliness in the common areas');

CREATE TABLE IF NOT EXISTS Visitors_s (
    visitor_id INT AUTO_INCREMENT PRIMARY KEY,
    visitor_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    resident_id INT,
    FOREIGN KEY (resident_id) REFERENCES Residents_s(resident_id)
);

INSERT INTO Visitors_s (visitor_name, phone_number, resident_id) VALUES
('Mark Johnson', '123-456-7890', 1),
('Emma White', '234-567-8901', 2),
('Sophie Brown', '345-678-9012', 3),
('Luke Wilson', '456-789-0123', 4),
('Lily Thomas', '567-890-1234', 5),
('Jack Davis', '678-901-2345', 6),
('Grace Martinez', '789-012-3456', 7),
('Leo Anderson', '890-123-4567', 8),
('Anna Taylor', '901-234-5678', 9),
('Ethan Smith', '012-345-6789', 10);
CREATE TABLE IF NOT EXISTS Househelp_s (
    househelp_id INT AUTO_INCREMENT PRIMARY KEY,
    househelp_name VARCHAR(100) NOT NULL,
    househelp_number VARCHAR(20) NOT NULL,
    househelp_availability BOOLEAN,
    block CHAR(1),
    FOREIGN KEY (block) REFERENCES Blocks(block)
);

INSERT INTO Househelp_s (househelp_name, househelp_number, househelp_availability, block) VALUES
('Maria', '111-222-3333', true, 'A'),
('Lucas', '222-333-4444', false, 'B'),
('Sophia', '333-444-5555', true, 'C'),
('James', '444-555-6666', true, 'D'),
('Isabella', '555-666-7777', false, 'A'),
('Daniel', '666-777-8888', true, 'B'),
('Charlotte', '777-888-9999', false, 'C'),
('William', '888-999-0000', true, 'D'),
('Mia', '999-000-1111', true, 'A'),
('Benjamin', '000-111-2222', false, 'B');


-- Create trigger to convert complain_text to uppercase
DELIMITER //
CREATE TRIGGER uppercase_complain_text
BEFORE INSERT ON Complaints_s
FOR EACH ROW
BEGIN
    SET NEW.complain_text = UPPER(NEW.complain_text);
END;
//
DELIMITER ;


commit;