package com.barber.appointmentservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                if (requestAttributes != null) {
                    final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
                    String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
                    if (authHeader != null) {
                        template.header(HttpHeaders.AUTHORIZATION, authHeader);
                        System.out.println("Authorization header added: " + authHeader);  // Para verificar que el token está siendo añadido
                    } else {
                        System.out.println("Authorization header is null");
                    }
                }
            }
        };
    }
}
