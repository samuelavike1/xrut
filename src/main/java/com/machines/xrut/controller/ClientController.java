package com.machines.xrut.controller;

import com.machines.xrut.dto.clientdto.ClientDto;
import com.machines.xrut.entity.Client;
import com.machines.xrut.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create-client")
    public ResponseEntity<Client> createClient(@RequestBody ClientDto clientDto) {
        Optional<Client> client = clientService.createClient(clientDto);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}