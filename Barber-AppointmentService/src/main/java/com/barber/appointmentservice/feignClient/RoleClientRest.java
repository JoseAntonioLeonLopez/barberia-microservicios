package com.barber.appointmentservice.feignClient;

import com.barber.userCommons.entity.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RoleService", url = "http://apigateway-service:8090/api/roles")
public interface RoleClientRest {

    @GetMapping("/name/{name}")
    Role getRoleByName(@PathVariable("name") String name);
}
