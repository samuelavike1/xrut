package com.machines.xrut.service.impl;

import com.machines.xrut.dto.clientdto.ClientDto;
import com.machines.xrut.dto.clientdto.ClientOvaTopUpDto;
import com.machines.xrut.entity.Client;
import com.machines.xrut.repository.ClientRepository;
import com.machines.xrut.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.security.SecureRandom;
import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String generateClientId() {
        Random random = new Random();
        return "XRC" + String.format("%08d", random.nextInt(100000000));
    }

    private String generateApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] apiKeyBytes = new byte[24];
        random.nextBytes(apiKeyBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(apiKeyBytes);
    }

    @Override
    public Optional<Client> createClient(ClientDto clientDto) {
        Client client = new Client();
        client.setClientId(generateClientId());
        client.setName(clientDto.name());
        client.setAddress(clientDto.address());
        client.setPhone(clientDto.phone());
        client.setEmail(clientDto.email());
        client.setOva(0.0);
        client.setApiKey(generateApiKey());
        repository.save(client);
        return Optional.of(client);
    }

    public Client getClientByApiKey(String apiKey) {
        return repository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API key")); // Use Optional for handling absence
    }


    public boolean isValidApiKey(String apiKey) {
        return repository.existsByApiKey(apiKey);
    }


    @Override
    public Optional<Client> updateClient(Long id, ClientDto clientDto){
       return repository.findById(id).map(client -> {
           client.setName(clientDto.name());
           client.setAddress(clientDto.address());
           client.setPhone(clientDto.phone());
           client.setEmail(clientDto.email());
           return repository.save(client);
       });
    }

    @Override
    public Optional<Client> ovaTopUp(ClientOvaTopUpDto clientOvaTopUpDto) {
        Optional<Client> clientOptional = repository.findByClientId(clientOvaTopUpDto.clientID());

        if (clientOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client Not Found" +clientOvaTopUpDto.clientID());
        }

        Client client = clientOptional.get();

        double balanceBefore = (client.getOva() != null ? client.getOva() : 0.0);
        double balanceAfter = (balanceBefore + clientOvaTopUpDto.amount());

        client.setOva(balanceAfter);
        repository.save(client);
        return Optional.empty();
    }
}
