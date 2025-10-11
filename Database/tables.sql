USE library_db;

-- Drop tables
DROP TABLE IF EXISTS fines;
DROP TABLE IF EXISTS issue;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS shelves;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS staff;

-- =========================
-- STAFF TABLE
-- =========================
CREATE TABLE staff (
  staff_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  role VARCHAR(20),
  username VARCHAR(20),
  password VARCHAR(20)
) ENGINE=InnoDB;

ALTER TABLE staff AUTO_INCREMENT = 10001;

-- =========================
-- MEMBERS TABLE
-- =========================
CREATE TABLE members (
  member_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  department VARCHAR(20),
  email VARCHAR(50),
  phone BIGINT,
  join_date DATE,
  membership ENUM('Free','Premium','Life-Time') DEFAULT 'Free'
) ENGINE=InnoDB;

ALTER TABLE members AUTO_INCREMENT = 256300;

-- =========================
-- SHELVES TABLE
-- =========================
CREATE TABLE shelves (
  category_id INT PRIMARY KEY,
  category_name VARCHAR(30)
) ENGINE=InnoDB;

-- =========================
-- BOOKS TABLE
-- =========================
CREATE TABLE books (
  book_id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INT,
  title VARCHAR(50),
  author VARCHAR(30),
  publisher VARCHAR(20),
  isbn BIGINT,
  quantity INT,
  available_copies INT,
  FOREIGN KEY (category_id) REFERENCES shelves(category_id)
) ENGINE=InnoDB;

ALTER TABLE books AUTO_INCREMENT = 100;

-- =========================
-- ISSUE TABLE
-- =========================
CREATE TABLE issue (
  issue_id INT AUTO_INCREMENT PRIMARY KEY,
  book_id INT,
  member_id INT,
  issue_date DATE DEFAULT (CURDATE()),
  due_date DATE,
  return_date DATE,
  UNIQUE (book_id, member_id),
  FOREIGN KEY (book_id) REFERENCES books(book_id),
  FOREIGN KEY (member_id) REFERENCES members(member_id)
) ENGINE=InnoDB;

-- =========================
-- FINES TABLE
-- =========================
CREATE TABLE fines (
  fine_id INT AUTO_INCREMENT PRIMARY KEY,
  member_id INT,
  book_id INT,
  amount DECIMAL(10,2),
  status ENUM('Unpaid', 'Paid') DEFAULT 'Unpaid',
  FOREIGN KEY (member_id) REFERENCES members(member_id),
  FOREIGN KEY (book_id) REFERENCES books(book_id)
) ENGINE=InnoDB;

-- =========================
-- LOGIN TABLE
-- =========================
CREATE TABLE login (
  login_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE,
  password VARCHAR(255),
  role ENUM('staff', 'member'),
  staff_id INT NULL,
  member_id INT NULL,
  FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (member_id) REFERENCES members(member_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;
