# Financial_Product_2025

### Инструкция по запуску проекта:

Что бы запустить проект, у вас должен быть установлен и запущен [docker](https://www.docker.com/).
После чего, необходимо выполнить следующие команды:
1) Для начала необходимо клонировать репозиторий ```git clone https://github.com/divergenny/financial_product_twenty_twenty_five.git```;
2) Открыть командную строку в корневом проекте и ввести: ```docker-compose -f docker-compose.yml up -d```;
3) Открываем postman и можем выполнять Rest Api запросы.

При запуске не из doker, необходимо соблюдать следующую последовательность запуска:
1) eureka 
2) config 
3) gateway 
4) auth-service
5) fin-service

Пользователь Admin: login: admin; password: admin .  

**API в разработанной информационной системе:**
1) POST /auth/register – регистрация нового пользователя
Request Body:: 
```json
{
    "username": "igor",
    "password": "password"
}
```
2) POST /auth/login – аутентификация пользователя и получение JWT-токена
В body указываем admin или своего созданного пользователя с паролем:
```json
{
    "username": "admin",
    "password": "admin"
}
```
3) GET /auth/validate?token=... – валидация JWT токена

#### Employee API (fin-service)
4) POST /users/add – создать сотрудника
Request Body:
```json
{
  "name": "Dmitriy",
  "balance": 1000
}
```
5) PUT /users/{id} – обновить данные сотрудника по ID
Request Body:
```json
{
"name": "Updated Name",
"balance": 1500
}
```
6) GET /users/{id} – получить информацию о сотруднике по ID

#### Transaction API (fin-service)
7) POST /transactions/add?employeeId=1&amount=500 – добавить транзакцию (приход/расход)
8) GET /transactions/user/{employeeId} – получить список транзакций конкретного сотрудника
9) GET /transactions/date?from=2025-07-01T00:00:00&to=2025-07-16T23:59:59 – получить транзакции по диапазону дат



### SQL для создания таблиц:

1) Для auth-service
```sql
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_status') THEN
CREATE TYPE user_status AS ENUM ('ACTIVE', 'INACTIVE', 'BANNED');
END IF;
END$$;


CREATE TABLE IF NOT EXISTS roles (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created TIMESTAMP NOT NULL DEFAULT now(),
    updated TIMESTAMP NOT NULL DEFAULT now()
);


CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    status user_status NOT NULL DEFAULT 'ACTIVE',
    created TIMESTAMP NOT NULL DEFAULT now(),
    updated TIMESTAMP NOT NULL DEFAULT now()
);


CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT uq_user_roles UNIQUE (user_id, role_id)
);


INSERT INTO roles (name, created, updated)
VALUES ('USER', now(), now()),
       ('ADMIN', now(), now())
    ON CONFLICT (name) DO NOTHING;


INSERT INTO users (username, first_name, last_name, email, password)
VALUES (        
        'admin',
        'Иван',
        'Иванов',
        'admin@gmail.ru',
        '$2a$12$j4OaI9w5NIUGKLynXyPuf.4LEAzW62x0/fqYvt3kJS/GY06EXFCOS'
) ON CONFLICT (username) DO NOTHING;


INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
    JOIN roles r ON r.name IN ('USER', 'ADMIN')
WHERE u.username = 'admin'
    ON CONFLICT DO NOTHING;
```

2) Для fin-service
```sql
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


INSERT INTO employees (name, balance)
VALUES ('Vlad', 3000),
       ('Igor', 2000);


INSERT INTO transactions (employee_id, amount, timestamp)
VALUES (1, 5000, now() - interval '1 day'),
       (1, -200, now()),
       (2, 1000, now() - interval '1 day'),
       (2, -100, now());
```

