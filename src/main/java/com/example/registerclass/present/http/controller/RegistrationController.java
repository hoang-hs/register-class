package com.example.registerclass.present.http.controller;

import com.example.registerclass.core.domain.Registration;
import com.example.registerclass.core.service.RegistrationService;
import com.example.registerclass.present.http.requests.RegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
