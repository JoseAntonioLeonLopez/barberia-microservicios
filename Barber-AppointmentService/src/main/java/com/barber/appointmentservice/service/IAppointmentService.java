package com.barber.appointmentservice.service;

import java.time.LocalDateTime;
import java.util.List;

import com.barber.appointmentservice.entity.Appointment;

public interface IAppointmentService {

    List<Appointment> findByClientId(Long clientId);

    List<Appointment> findByBarberId(Long barberId);

    List<Appointment> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    boolean existsByClientIdAndAppointmentDate(Long clientId, LocalDateTime appointmentDate);

    boolean existsByBarberIdAndAppointmentDate(Long barberId, LocalDateTime appointmentDate);

    List<Appointment> findAll();

    Appointment createAppointment(Appointment appointment);

    Appointment updateAppointment(Long id, Appointment appointmentDetails);

    void deleteAppointment(Long id);
}
