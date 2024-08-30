package com.barber.appointmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.barber.appointmentservice.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
	List<Appointment> findByClientId(Long clientId);

    List<Appointment> findByBarberId(Long barberId);

    List<Appointment> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    boolean existsByClientIdAndAppointmentDate(Long clientId, LocalDateTime appointmentDate);

    boolean existsByBarberIdAndAppointmentDate(Long barberId, LocalDateTime appointmentDate);
}
