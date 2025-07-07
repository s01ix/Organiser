CREATE TABLE  users (
    user_id     SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(45)  NOT NULL,
    password    VARCHAR(100) NOT NULL,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE  classes (
    class_id             SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(100)  NOT NULL,
    description          VARCHAR(500),
    has_reports          ENUM('Y','N') DEFAULT 'N',
    has_entry_tests      ENUM('Y','N') DEFAULT 'N',
    reports_required     TINYINT UNSIGNED,
    entry_tests_required TINYINT UNSIGNED
);

CREATE TABLE  class_schedule (
    schedule_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    class_id    SMALLINT UNSIGNED NOT NULL,
    start_time  DATETIME NOT NULL,
    end_time    DATETIME NOT NULL,
    room_number VARCHAR(10),
    CONSTRAINT fk_schedule_class
        FOREIGN KEY (class_id)
        REFERENCES classes(class_id)
        ON DELETE CASCADE
);

CREATE TABLE  projects (
    project_id  INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    class_id    SMALLINT UNSIGNED NOT NULL,
    name        VARCHAR(100)  NOT NULL,
    description VARCHAR(1000),
    deadline1   DATETIME,
    deadline2   DATETIME,
    deadline3   DATETIME,
    CONSTRAINT fk_project_class
        FOREIGN KEY (class_id)
        REFERENCES classes(class_id)
        ON DELETE CASCADE
);

CREATE TABLE  exams (
    exam_id    INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    class_id   SMALLINT UNSIGNED NOT NULL,
    type       ENUM('midterm', 'final', 'quiz') DEFAULT 'midterm',
    exam_date  DATE NOT NULL,
    FOREIGN KEY (class_id) REFERENCES classes(class_id)
        ON DELETE CASCADE
);