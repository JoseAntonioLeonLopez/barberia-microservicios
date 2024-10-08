package com.barber.appointmentservice.feignClient;

import com.barber.userCommons.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService", url = "${url.users}") // Define el nombre del servicio y la URL para el servicio de usuarios.
public interface UserClientRest {

    @GetMapping("/{userId}") // Define el mapeo HTTP para obtener un usuario por su ID.
    UserDTO getUserById(@PathVariable("userId") Long userId); // Método que invoca el servicio para obtener un usuario.
}
