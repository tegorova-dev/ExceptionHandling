package org.example.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.response.DefaultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RouteAlfaCusIdController {

    @PostMapping("/v1/alfa_auth")
    public ResponseEntity<DefaultResponse> alfaAuth(@NotNull @RequestHeader(value = "X-Correlation-ID") String corrId) {
        return ResponseEntity.ok(new DefaultResponse());
    }
}
