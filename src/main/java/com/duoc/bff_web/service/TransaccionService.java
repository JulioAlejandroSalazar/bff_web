package com.duoc.bff_web.service;

import com.duoc.bff_web.dto.TransaccionDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class TransaccionService {

    private final RestTemplate restTemplate;

    public TransaccionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "transaccionService", fallbackMethod = "fallbackTransacciones")
    @Retry(name = "transaccionService")
    public List<TransaccionDto> obtenerTransacciones() {
        String url = "http://lector-batch:8080/api/transacciones";
        TransaccionDto[] transacciones = restTemplate.getForObject(url, TransaccionDto[].class);
        return transacciones != null ? Arrays.asList(transacciones) : List.of();
    }

    public List<TransaccionDto> fallbackTransacciones(Exception e) {
        System.out.println("fallback llamado: " + e.getMessage());
        return List.of(); // retorna lista vacia en caso de fallo
    }
}
