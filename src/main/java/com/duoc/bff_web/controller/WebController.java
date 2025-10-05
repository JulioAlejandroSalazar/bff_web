package com.duoc.bff_web.controller;

import com.duoc.bff_web.dto.CuentaAnualDto;
import com.duoc.bff_web.dto.InteresDto;
import com.duoc.bff_web.dto.TransaccionDto;
import com.duoc.bff_web.service.TransaccionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/bff/web")
public class WebController {

    private final RestTemplate restTemplate;
    private final TransaccionService transaccionService;

    public WebController(RestTemplate restTemplate, TransaccionService transaccionService) {
        this.restTemplate = restTemplate;
        this.transaccionService = transaccionService;
    }

    @GetMapping("/transacciones")
    public List<TransaccionDto> getTransaccionesWeb() {
        return transaccionService.obtenerTransacciones();
    }

    @GetMapping("/intereses")
    public List<InteresDto> getInteresesWeb() {
        String url = "http://ms-intereses/api/intereses";
        InteresDto[] intereses = restTemplate.getForObject(url, InteresDto[].class);
        return intereses != null ? Arrays.asList(intereses) : List.of();
    }

    @GetMapping("/cuentas_anuales")
    public List<CuentaAnualDto> getCuentasAnualesWeb() {
        String url = "http://ms-cuentas-anuales/api/cuentas_anuales";
        CuentaAnualDto[] cuentas = restTemplate.getForObject(url, CuentaAnualDto[].class);
        return cuentas != null ? Arrays.asList(cuentas) : List.of();
    }
}
