package com.barber.appointmentservice.feignClient;

import com.barber.userCommons.entity.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RoleService", url = "${url.roles}") // Define el nombre del servicio y la URL para el servicio de roles.
public interface RoleClientRest {

    @GetMapping("/name/{name}") // Define el mapeo HTTP para obtener un rol por su nombre.
    Role getRoleByName(@PathVariable("name") String name); // Método que invoca el servicio para obtener un rol.
}
