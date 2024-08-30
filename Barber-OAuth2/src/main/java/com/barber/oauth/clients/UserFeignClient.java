package com.barber.oauth.clients;

import com.barber.userCommons.entity.*;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService", url = "http://apigateway-service:8090/api/users")
public interface UserFeignClient {

    @GetMapping("/email/{email}")
    User getUserByEmail(@PathVariable String email);

}
