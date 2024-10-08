package com.barber.appointmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.barber.appointmentservice.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    // Obtener citas por ID de cliente.
    List<Appointment> findByClientId(Long clientId);

    // Obtener citas por ID de barbero.
    List<Appointment> findByBarberId(Long barberId);

    // Obtener citas en un rango de fechas.
    List<Appointment> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Verificar si existe una cita para un cliente en una fecha específica.
    boolean existsByClientIdAndAppointmentDate(Long clientId, LocalDateTime appointmentDate);

    // Verificar si existe una cita para un barbero en una fecha específica.
    boolean existsByBarberIdAndAppointmentDate(Long barberId, LocalDateTime appointmentDate);
    
    // Eliminar todas las citas asociadas a un cliente.
    void deleteAppointmentByClientId(Long clientId);
}
