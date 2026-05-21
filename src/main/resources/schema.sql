CREATE TABLE employee_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL
);

INSERT INTO employee_records (id, first_name, last_name, salary)
SELECT
    LEVEL,
    'FirstName_' || LEVEL,
    'LastName_' || LEVEL,
    DBMS_RANDOM.VALUE(50000, 150000)
FROM dual
CONNECT BY LEVEL <= 1000000;

COMMIT;