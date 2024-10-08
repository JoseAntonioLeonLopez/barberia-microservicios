package com.barber.userservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Interfaz Feign para comunicar con el servicio de citas
@FeignClient(name = "AppointmentService", url = "${url.appointments}")
public interface AppointmentClientRest {
	
    // Método para eliminar una cita por ID de cliente
    @DeleteMapping("/client/{clientId}")
    ResponseEntity<?> deleteAppointmentByClientId(@PathVariable("clientId") Long clientId);
}
