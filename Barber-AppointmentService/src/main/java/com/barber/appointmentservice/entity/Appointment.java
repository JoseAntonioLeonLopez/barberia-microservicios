package com.barber.appointmentservice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(
    name = "appointments", 
    uniqueConstraints = { 
        @UniqueConstraint(columnNames = {"client_id", "appointment_date"}), // Restringe que no puede haber dos citas con el mismo cliente y fecha.
        @UniqueConstraint(columnNames = {"barber_id", "appointment_date"})  // Restringe que no puede haber dos citas con el mismo barbero y fecha.
    }
)
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder // Proporciona el patrón de diseño Builder para crear instancias de Appointment.
public class Appointment {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 

    @Column(name = "client_id", nullable = false) 
    private Long clientId; 

    @Column(name = "barber_id", nullable = false) 
    private Long barberId;

    @Column(name = "appointment_date", nullable = false) 
    private LocalDateTime appointmentDate; 

    @Column(name = "created_at", nullable = false, updatable = false) 
    @Builder.Default // El valor por defecto será la fecha y hora actual.
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false) 
    @Builder.Default // El valor por defecto será la fecha y hora actual.
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Este método se ejecuta antes de que una entidad sea actualizada.
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(); // Actualiza el campo updatedAt con la fecha y hora actual antes de la actualización.
    }
}
