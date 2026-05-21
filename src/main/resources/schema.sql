CREATE TABLE employee_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL
);

-- INSERT INTO employee_records (id, first_name, last_name, salary)
-- SELECT 
--     X, 
--     'FirstName_' || X, 
--     'LastName_' || X, 
--     50000 + (RAND() * 100000)
-- FROM SYSTEM_RANGE(1, 1000000);
-- COMMIT;