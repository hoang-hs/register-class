package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Inventory;
import com.example.registerclass.core.domain.Registration;
import com.example.registerclass.core.domain.repository.InventoryRepository;
import com.example.registerclass.core.domain.repository.RegistrationRepository;
import com.example.registerclass.core.enums.StatusRegistration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConsumerService {
    private final RegistrationRepository registrationRepository;
    private final InventoryRepository inventoryRepository;

    public void handleRecord(Registration registration) {
        switch (registration.getStatus()) {
            case NEW -> registerClass(registration);
            case CANCEL -> cancelClass(registration);
            default -> log.error("status invalid, registration: {}", registration);
        }
    }

    public void registerClass(Registration registration) {
        Optional<Inventory> optionalInventory = inventoryRepository.findByCourse(registration.getCourse());
        if (optionalInventory.isEmpty()) {
            log.error("inventory is empty, course :{}", registration.getCourse());
            return;
        }
        Inventory inventory = optionalInventory.get();
        StatusRegistration status;
        if (inventory.isAvailable()) {
            status = StatusRegistration.SUCCESS;
            inventory.setTotalReserved(inventory.getTotalReserved() + 1);
            inventoryRepository.save(inventory);
        } else {
            status = StatusRegistration.FAIL;
        }
        registration.setStatus(status);
        registrationRepository.save(registration);
    }

    public void cancelClass(Registration registration) {
        Optional<Inventory> optionalInventory = inventoryRepository.findByCourse(registration.getCourse());
        if (optionalInventory.isEmpty()) {
            log.error("inventory is empty, course :{}", registration.getCourse());
            return;
        }
        Inventory inventory = optionalInventory.get();
        inventory.setTotalReserved(inventory.getTotalReserved() - 1);
        inventoryRepository.save(inventory);

        registration.setStatus(StatusRegistration.CANCEL);
        registrationRepository.save(registration);
    }
}
