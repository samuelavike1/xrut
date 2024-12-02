package com.machines.xrut.service;

import com.machines.xrut.dto.clientdto.ClientDto;
import com.machines.xrut.dto.clientdto.ClientOvaTopUpDto;
import com.machines.xrut.entity.Client;
import org.springframework.security.core.Authentication;


import java.util.Optional;


public interface ClientService {
    Optional<Client> createClient(ClientDto clientDto);
    Optional<Client> updateClient(Long id, ClientDto clientDto);
    Optional<Client> ovaTopUp(ClientOvaTopUpDto clientOvaTopUpDto);
    boolean isValidApiKey(String apiKey);
    Client getClientByApiKey(String apiKey);

//    Authentication getAuthentication(String apiKey);
}
