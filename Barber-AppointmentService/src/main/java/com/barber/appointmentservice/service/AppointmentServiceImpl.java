package com.barber.appointmentservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.barber.appointmentservice.entity.Appointment;
import com.barber.appointmentservice.feignClient.RoleClientRest;
import com.barber.appointmentservice.feignClient.UserClientRest;
import com.barber.userCommons.dto.UserDTO;
import com.barber.userCommons.entity.Role;

import com.barber.appointmentservice.repository.AppointmentRepository;

@Service
@Primary
public class AppointmentServiceImpl implements IAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserClientRest userClientRest;

    @Autowired
    private RoleClientRest roleClientRest;

    @Override
    public List<Appointment> findByClientId(Long clientId) {
        validateUserRole(clientId, "CLIENT");
        return appointmentRepository.findByClientId(clientId);
    }

    @Override
    public List<Appointment> findByBarberId(Long barberId) {
        validateUserRole(barberId, "BARBER");
        return appointmentRepository.findByBarberId(barberId);
    }

    @Override
    public List<Appointment> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return appointmentRepository.findByAppointmentDateBetween(startDate, endDate);
    }

    @Override
    public boolean existsByClientIdAndAppointmentDate(Long clientId, LocalDateTime appointmentDate) {
        return appointmentRepository.existsByClientIdAndAppointmentDate(clientId, appointmentDate);
    }

    @Override
    public boolean existsByBarberIdAndAppointmentDate(Long barberId, LocalDateTime appointmentDate) {
        return appointmentRepository.existsByBarberIdAndAppointmentDate(barberId, appointmentDate);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        validateUserRole(appointment.getClientId(), "CLIENT");
        validateUserRole(appointment.getBarberId(), "BARBER");

        if (existsByClientIdAndAppointmentDate(appointment.getClientId(), appointment.getAppointmentDate())) {
            throw new RuntimeException("Client already has an appointment at this date and time.");
        }

        if (existsByBarberIdAndAppointmentDate(appointment.getBarberId(), appointment.getAppointmentDate())) {
            throw new RuntimeException("Barber already has an appointment at this date and time.");
        }

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            validateUserRole(appointmentDetails.getClientId(), "CLIENT");
            validateUserRole(appointmentDetails.getBarberId(), "BARBER");
            appointment.setClientId(appointmentDetails.getClientId());
            appointment.setBarberId(appointmentDetails.getBarberId());
            appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
            // Actualizar el campo `updatedAt` a la fecha y hora actuales
            appointment.setUpdatedAt(LocalDateTime.now());
            return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Appointment not found with id " + id);
        }
    }

    @Override
    public void deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Appointment not found with id " + id);
        }
    }

    private void validateUserRole(Long userId, String expectedRoleName) {

        UserDTO userDTO = userClientRest.getUserById(userId);
        if (userDTO == null) {
            throw new RuntimeException("User with id " + userId + " not found.");
        }

        Role role = roleClientRest.getRoleByName(expectedRoleName);
        if (role == null) {
            throw new RuntimeException("Role with name " + expectedRoleName + " not found.");
        }

        if (!role.getId().equals(userDTO.getRoleId())) {
            throw new RuntimeException("User with email " + userDTO.getEmail() + " is not a " + expectedRoleName + ".");
        }
    }
}
