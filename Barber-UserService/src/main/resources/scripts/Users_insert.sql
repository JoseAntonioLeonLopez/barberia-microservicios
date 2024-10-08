-- Insertar roles si no existen
INSERT IGNORE INTO roles (name) VALUES ('ADMIN');
INSERT IGNORE INTO roles (name) VALUES ('CLIENT');
INSERT IGNORE INTO roles (name) VALUES ('BARBER');

-- Insertar administrador si no existe
INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'admin@example.com', '$2y$10$BulEsVUSUPTItJ6YjvagquvJGc24.uha3Atd/RnqV656SDtvFnCua', 'Admin', 'Surname1', 'Surname2', '665243995', 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');

-- Insertar clientes si no existen
INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'joseantonio@example.com', '$2y$10$BulEsVUSUPTItJ6YjvagquvJGc24.uha3Atd/RnqV656SDtvFnCua', 'José Antonio', 'León', 'López', '704288223', 2
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'joseantonio@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'javier@example.com', '$2y$10$BulEsVUSUPTItJ6YjvagquvJGc24.uha3Atd/RnqV656SDtvFnCua', 'Javier', 'Jimenez', 'Bohorquez', '724978995', 2
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'javier@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'moises@example.com', '$2y$10$BulEsVUSUPTItJ6YjvagquvJGc24.uha3Atd/RnqV656SDtvFnCua', 'Moisés', 'Pérez', 'Donoso', '747210546', 2
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'moises@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'mike@example.com', '$2y$10$BulEsVUSUPTItJ6YjvagquvJGc24.uha3Atd/RnqV656SDtvFnCua', 'Mike', 'Roe', '', '752147716', 2
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'mike@example.com');

-- Insertar barberos si no existen
INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'saul@example.com', '$2y$10$BulEsVUSUPTItJ6YjvagquvJGc24.uha3Atd/RnqV656SDtvFnCua', 'Saúl', 'Capitas', 'Casas', '754483445', 3
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'saul@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'isaac@example.com', '$2y$10$BulEsVUSUPTItJ6YjvagquvJGc24.uha3Atd/RnqV656SDtvFnCua', 'Isaac', 'Benitez', 'Barea', '757945479', 3
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'isaac@example.com');

INSERT INTO users (email, password, name, first_surname, second_surname, phone_number, role_id)
SELECT 'manuel@example.com', '$2y$10$BulEsVUSUPTItJ6YjvagquvJGc24.uha3Atd/RnqV656SDtvFnCua', 'Manuel', 'Pelloso', 'Ronquillo', '767912887', 3
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'manuel@example.com');
