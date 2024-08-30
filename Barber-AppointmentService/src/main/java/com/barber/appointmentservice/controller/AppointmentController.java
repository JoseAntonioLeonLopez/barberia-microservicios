package com.barber.appointmentservice.controller;

import com.barber.appointmentservice.entity.Appointment;
import com.barber.appointmentservice.service.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentServiceImpl appointmentService;

    // Crear una nueva cita
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment createdAppointment = appointmentService.createAppointment(appointment);
            return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar una cita existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable("id") Long id,
                                                @RequestBody Appointment appointmentDetails) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDetails);
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una cita existente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("id") Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todas las citas
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Appointment> appointments = appointmentService.findAll();
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Obtener citas por ID de cliente
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> findByClientId(@PathVariable("clientId") Long clientId) {
        List<Appointment> appointments = appointmentService.findByClientId(clientId);
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Obtener citas por ID de barbero
    @GetMapping("/barber/{barberId}")
    public ResponseEntity<?> findByBarberId(@PathVariable("barberId") Long barberId) {
        List<Appointment> appointments = appointmentService.findByBarberId(barberId);
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Obtener citas entre fechas
    @GetMapping("/date-range")
    public ResponseEntity<?> findByAppointmentDateBetween(
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            return new ResponseEntity<>("Start date must be before end date", HttpStatus.BAD_REQUEST);
        }
        List<Appointment> appointments = appointmentService.findByAppointmentDateBetween(startDate, endDate);
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Verificar si existe una cita para un cliente en una fecha
    @GetMapping("/exists/client")
    public ResponseEntity<?> existsByClientIdAndAppointmentDate(
            @RequestParam("clientId") Long clientId,
            @RequestParam("appointmentDate") LocalDateTime appointmentDate) {
        boolean exists = appointmentService.existsByClientIdAndAppointmentDate(clientId, appointmentDate);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Verificar si existe una cita para un barbero en una fecha
    @GetMapping("/exists/barber")
    public ResponseEntity<?> existsByBarberIdAndAppointmentDate(
            @RequestParam("barberId") Long barberId,
            @RequestParam("appointmentDate") LocalDateTime appointmentDate) {
        boolean exists = appointmentService.existsByBarberIdAndAppointmentDate(barberId, appointmentDate);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
