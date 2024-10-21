-- Cita 1
INSERT INTO appointments (client_id, barber_id, appointment_date)
SELECT 2, 6, '2024-11-30T10:00:00'
WHERE NOT EXISTS (
    SELECT 1 FROM appointments
    WHERE (client_id = 2 AND appointment_date = '2024-11-30T10:00:00')
    OR (barber_id = 6 AND appointment_date = '2024-11-30T10:00:00')
);

-- Cita 2
INSERT INTO appointments (client_id, barber_id, appointment_date)
SELECT 3, 7, '2024-11-09T13:00:00'
WHERE NOT EXISTS (
    SELECT 1 FROM appointments
    WHERE (client_id = 3 AND appointment_date = '2024-11-09T13:00:00')
    OR (barber_id = 7 AND appointment_date = '2024-11-09T13:00:00')
);

-- Cita 3
INSERT INTO appointments (client_id, barber_id, appointment_date)
SELECT 4, 8, '2024-11-08T13:30:00'
WHERE NOT EXISTS (
    SELECT 1 FROM appointments
    WHERE (client_id = 4 AND appointment_date = '2024-11-08T13:30:00')
    OR (barber_id = 8 AND appointment_date = '2024-11-08T13:30:00')
);

-- Cita 4
INSERT INTO appointments (client_id, barber_id, appointment_date)
SELECT 2, 6, '2024-11-07T10:00:00'
WHERE NOT EXISTS (
    SELECT 1 FROM appointments
    WHERE (client_id = 2 AND appointment_date = '2024-11-07T10:00:00')
    OR (barber_id = 6 AND appointment_date = '2024-11-07T10:00:00')
);

-- Cita 5
INSERT INTO appointments (client_id, barber_id, appointment_date)
SELECT 3, 7, '2024-11-09T13:30:00'
WHERE NOT EXISTS (
    SELECT 1 FROM appointments
    WHERE (client_id = 3 AND appointment_date = '2024-11-09T13:30:00')
    OR (barber_id = 7 AND appointment_date = '2024-11-09T13:30:00')
);

-- Cita 6
INSERT INTO appointments (client_id, barber_id, appointment_date)
SELECT 4, 8, '2024-11-09T09:30:00'
WHERE NOT EXISTS (
    SELECT 1 FROM appointments
    WHERE (client_id = 4 AND appointment_date = '2024-11-09T09:30:00')
    OR (barber_id = 8 AND appointment_date = '2024-11-09T09:30:00')
);