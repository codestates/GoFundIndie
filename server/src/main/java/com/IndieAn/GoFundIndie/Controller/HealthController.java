package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.HealthDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthController {
    private final HealthDTO health = HealthDTO.builder().server("running").health("OK").build();

    @GetMapping(value = "/")
    public ResponseEntity<?> ServerHealthCheck () {
        log.info("Sever health check");
        return ResponseEntity.status(200).body(health);
    }
}
