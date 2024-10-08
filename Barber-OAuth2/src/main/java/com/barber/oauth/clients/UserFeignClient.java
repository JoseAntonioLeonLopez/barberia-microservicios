package com.barber.oauth.clients;

import com.barber.userCommons.entity.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService", url = "${url.users}")
public interface UserFeignClient {

    // Obtener un usuario por su dirección de correo electrónico
    @GetMapping("/email/{email}")
    User getUserByEmail(@PathVariable String email);
}
