package com.example.registerclass.present.http.controller;

import com.example.registerclass.core.domain.Registration;
import com.example.registerclass.core.service.RegistrationService;
import com.example.registerclass.present.http.requests.GetRegistrationRequest;
import com.example.registerclass.present.http.requests.RegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("")
    Registration save(@RequestBody @Valid RegistrationRequest req) {
        return registrationService.save(req);
    }

    @PutMapping("/cancel/{id}")
    Registration cancel(@PathVariable Long id) {
        return registrationService.cancel(id);
    }

    @GetMapping("")
    List<Registration> get(GetRegistrationRequest req) {
        return registrationService.get(req);
    }
}
