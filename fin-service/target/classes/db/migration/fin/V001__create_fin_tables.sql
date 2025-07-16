CREATE TABLE employees
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255)   NOT NULL,
    balance NUMERIC(19, 2) NOT NULL DEFAULT 0
);

CREATE TABLE transactions
(
    id        SERIAL PRIMARY KEY,
    employee_id   BIGINT         NOT NULL,
    amount    NUMERIC(19, 2) NOT NULL,
    timestamp TIMESTAMP      NOT NULL,
    CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employees (id)
);
