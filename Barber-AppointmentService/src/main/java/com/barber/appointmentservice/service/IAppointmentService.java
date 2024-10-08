package com.barber.appointmentservice.service;

import java.time.LocalDateTime;
import java.util.List;

import com.barber.appointmentservice.entity.Appointment;

public interface IAppointmentService {

    // Obtener citas por ID de cliente
    List<Appointment> findByClientId(Long clientId);

    // Obtener citas por ID de barbero
    List<Appointment> findByBarberId(Long barberId);

    // Obtener citas dentro de un rango de fechas
    List<Appointment> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Verificar si el cliente tiene una cita en una fecha específica
    boolean existsByClientIdAndAppointmentDate(Long clientId, LocalDateTime appointmentDate);

    // Verificar si el barbero tiene una cita en una fecha específica
    boolean existsByBarberIdAndAppointmentDate(Long barberId, LocalDateTime appointmentDate);

    // Obtener todas las citas
    List<Appointment> findAll();

    // Crear una nueva cita
    Appointment createAppointment(Appointment appointment);

    // Actualizar una cita existente
    Appointment updateAppointment(Long id, Appointment appointmentDetails);

    // Eliminar una cita por ID
    void deleteAppointment(Long id);
    
    // Eliminar todas las citas de un cliente específico
    void deleteAppointmentByClientId(Long clientId);
}
