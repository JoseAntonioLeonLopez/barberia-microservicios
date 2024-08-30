package com.barber.appointmentservice.feignClient;

import com.barber.userCommons.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService", url = "http://apigateway-service:8090/api/users")
public interface UserClientRest {

    @GetMapping("/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);
}
