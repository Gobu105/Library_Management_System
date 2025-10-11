USE library_db;

-- =========================
-- STAFF DATA
-- =========================
INSERT INTO staff (name, role, username, password) VALUES
('Ravi Kumar', 'Librarian', 'ravi.k', 'pass123'),
('Priya Sharma', 'Assistant', 'priya.s', 'pass456'),
('Arjun Patel', 'Clerk', 'arjun.p', 'pass789'),
('Meena Reddy', 'Librarian', 'meena.r', 'lib123'),
('Vikram Das', 'Assistant', 'vikram.d', 'lib456'),
('Kiran Rao', 'Technician', 'kiran.r', 'tech123'),
('Amit Verma', 'Manager', 'amit.v', 'mgr456'),
('Sunita Iyer', 'Assistant', 'sunita.i', 'aid789'),
('Rajesh Nair', 'Clerk', 'rajesh.n', 'pwd999'),
('Deepa Singh', 'Librarian', 'deepa.s', 'readme');

-- =========================
-- MEMBERS DATA
-- =========================
INSERT INTO members (name, department, email, phone, join_date, membership) VALUES
('Anil Kumar', 'CSE', 'anil.cse@college.edu', 9876543210, '2023-06-01', 'Premium'),
('Bhavna Joshi', 'ECE', 'bhavna.ece@college.edu', 9876543211, '2023-07-15', 'Free'),
('Chirag Mehta', 'MECH', 'chirag.mech@college.edu', 9876543212, '2022-08-10', 'Life-Time'),
('Divya Nair', 'EEE', 'divya.eee@college.edu', 9876543213, '2021-09-12', 'Premium'),
('Eshan Gupta', 'CSE', 'eshan.cse@college.edu', 9876543214, '2023-01-01', 'Free'),
('Farhan Ali', 'CIVIL', 'farhan.civil@college.edu', 9876543215, '2024-03-22', 'Premium'),
('Gita Reddy', 'IT', 'gita.it@college.edu', 9876543216, '2023-10-01', 'Free'),
('Harsh Jain', 'CSE', 'harsh.cse@college.edu', 9876543217, '2022-11-10', 'Life-Time'),
('Isha Kapoor', 'ECE', 'isha.ece@college.edu', 9876543218, '2023-09-12', 'Premium'),
('Jay Patel', 'MECH', 'jay.mech@college.edu', 9876543219, '2022-07-30', 'Free');

-- =========================
-- SHELVES DATA
-- =========================
INSERT INTO shelves (category_id, category_name) VALUES
(1, 'Computer Science'),
(2, 'Electronics'),
(3, 'Mechanical'),
(4, 'Electrical'),
(5, 'Civil Engineering'),
(6, 'Mathematics'),
(7, 'Physics'),
(8, 'Literature');

-- =========================
-- BOOKS DATA
-- =========================
INSERT INTO books (category_id, title, author, publisher, isbn, quantity, available_copies) VALUES
(1, 'Introduction to Algorithms', 'Thomas H. Cormen', 'MIT Press', 9780262033848, 10, 7),
(1, 'Python Programming', 'Guido van Rossum', 'OReilly', 9781449355739, 15, 9),
(1, 'Data Structures in C', 'Reema Thareja', 'Oxford', 9780198085470, 12, 6),
(2, 'Microelectronic Circuits', 'Sedra & Smith', 'Oxford', 9780199339138, 8, 5),
(2, 'Digital Logic Design', 'M. Morris Mano', 'Pearson', 9780134549897, 10, 4),
(3, 'Engineering Thermodynamics', 'P.K. Nag', 'Tata McGraw-Hill', 9780070151314, 9, 6),
(3, 'Machine Design', 'R.S. Khurmi', 'S Chand', 9788121925372, 11, 5),
(4, 'Electrical Technology', 'B.L. Theraja', 'S Chand', 9788121924375, 14, 10),
(5, 'Surveying Vol 1', 'B.C. Punmia', 'Laxmi Publications', 9788131807631, 7, 4),
(6, 'Discrete Mathematics', 'Rosen', 'McGraw-Hill', 9780073383095, 13, 11),
(7, 'Concepts of Physics', 'H.C. Verma', 'Bharati Bhawan', 9788177091878, 20, 18),
(8, 'Hamlet', 'William Shakespeare', 'Penguin', 9780141015866, 5, 3),
(8, 'Pride and Prejudice', 'Jane Austen', 'Oxford', 9780192833554, 6, 4),
(1, 'Database Systems Concepts', 'Silberschatz', 'McGraw-Hill', 9780078022159, 10, 7),
(1, 'Operating System Concepts', 'Abraham Silberschatz', 'Wiley', 9781119456339, 9, 4),
(6, 'Linear Algebra Done Right', 'Axler', 'Springer', 9783319110790, 8, 6),
(2, 'Signals and Systems', 'Alan Oppenheim', 'Pearson', 9780138147570, 10, 8),
(7, 'University Physics', 'Young and Freedman', 'Pearson', 9780321500762, 12, 9),
(8, 'Macbeth', 'William Shakespeare', 'Penguin', 9780141396316, 5, 2),
(5, 'Structural Analysis', 'R.C. Hibbeler', 'Pearson', 9780134582139, 7, 5);

-- =========================
-- ISSUE DATA
-- =========================
INSERT INTO issue (book_id, member_id, issue_date, due_date, return_date) VALUES
(100, 256300, '2025-09-01', '2025-09-15', '2025-09-10'),
(101, 256301, '2025-08-20', '2025-09-05', NULL),
(102, 256302, '2025-09-02', '2025-09-16', '2025-09-14'),
(103, 256303, '2025-09-03', '2025-09-17', NULL),
(104, 256304, '2025-08-29', '2025-09-12', '2025-09-11'),
(105, 256305, '2025-09-04', '2025-09-18', NULL),
(106, 256306, '2025-09-01', '2025-09-15', NULL),
(107, 256307, '2025-09-06', '2025-09-20', NULL),
(108, 256308, '2025-08-25', '2025-09-08', '2025-09-06'),
(109, 256309, '2025-09-02', '2025-09-16', NULL);

-- =========================
-- FINES DATA
-- =========================
INSERT INTO fines (member_id, book_id, amount, status) VALUES
(256301, 101, 50.00, 'Unpaid'),
(256303, 103, 30.00, 'Paid'),
(256306, 106, 75.00, 'Unpaid'),
(256307, 107, 20.00, 'Unpaid'),
(256308, 108, 10.00, 'Paid'),
(256309, 109, 100.00, 'Unpaid');

-- =========================
-- LOGIN DATA
-- =========================
INSERT INTO login (username, password, role, staff_id, member_id) VALUES
('ravi.k', 'pass123', 'staff', 10001, NULL),
('priya.s', 'pass456', 'staff', 10002, NULL),
('arjun.p', 'pass789', 'staff', 10003, NULL),
('meena.r', 'lib123', 'staff', 10004, NULL),
('vikram.d', 'lib456', 'staff', 10005, NULL),
('anil.cse', 'user123', 'member', NULL, 256300),
('bhavna.ece', 'user456', 'member', NULL, 256301),
('chirag.mech', 'user789', 'member', NULL, 256302),
('divya.eee', 'user000', 'member', NULL, 256303),
('eshan.cse', 'user111', 'member', NULL, 256304);


-- =========================
-- Display tables
-- =========================
SELECT * FROM staff;
SELECT * FROM members;
SELECT * FROM shelves;
SELECT * FROM books;
SELECT * FROM issue;
SELECT * FROM fines;
SELECT * FROM login;

