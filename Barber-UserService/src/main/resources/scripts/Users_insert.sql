-- Insertar roles si no existen
INSERT IGNORE INTO roles (name) VALUES ('ADMIN');
INSERT IGNORE INTO roles (name) VALUES ('CLIENT');
INSERT IGNORE INTO roles (name) VALUES ('BARBER');

-- Insertar administrador si no existe
INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'admin@example.com', '$2a$10$FgFzoMbAZjVvKOVkNgNzieyr0y5VjQn.FdEiL5vRYcuPqHxFq6MSG', 'Admin', 'Surname1', 'Surname2', '555-0001', 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');

-- Insertar clientes si no existen
INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'cliente1@example.com', '$2a$10$FgFzoMbAZjVvKOVkNgNzieyr0y5VjQn.FdEiL5vRYcuPqHxFq6MSG', 'Cliente1', 'Roe', '', '555-0002', 2
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'cliente1@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'cliente2@example.com', '$2a$10$FgFzoMbAZjVvKOVkNgNzieyr0y5VjQn.FdEiL5vRYcuPqHxFq6MSG', 'Cliente2', 'Roe', '', '555-0003', 2
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'cliente2@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'cliente3@example.com', '$2a$10$FgFzoMbAZjVvKOVkNgNzieyr0y5VjQn.FdEiL5vRYcuPqHxFq6MSG', 'Cliente3', 'Roe', '', '555-0004', 2
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'cliente3@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'cliente4@example.com', '$2a$10$FgFzoMbAZjVvKOVkNgNzieyr0y5VjQn.FdEiL5vRYcuPqHxFq6MSG', 'Cliente4', 'Roe', '', '555-0005', 2
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'cliente4@example.com');

-- Insertar barberos si no existen
INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'barbero1@example.com', '$2a$10$FgFzoMbAZjVvKOVkNgNzieyr0y5VjQn.FdEiL5vRYcuPqHxFq6MSG', 'Barbero1', 'Smith', '', '555-0006', 3
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'barbero1@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'barbero2@example.com', '$2a$10$FgFzoMbAZjVvKOVkNgNzieyr0y5VjQn.FdEiL5vRYcuPqHxFq6MSG', 'Barbero2', 'Smith', '', '555-0007', 3
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'barbero2@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'barbero3@example.com', '$2a$10$FgFzoMbAZjVvKOVkNgNzieyr0y5VjQn.FdEiL5vRYcuPqHxFq6MSG', 'Barbero3', 'Smith', '', '555-0008', 3
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'barbero3@example.com');
