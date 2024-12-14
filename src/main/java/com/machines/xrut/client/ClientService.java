package com.machines.xrut.client;

import com.machines.xrut.client.clientdto.ClientDto;
import com.machines.xrut.client.clientdto.ClientOvaTopUpDto;


import java.util.Optional;


public interface ClientService {
    Optional<Client> createClient(ClientDto clientDto);
    Optional<Client> updateClient(Long id, ClientDto clientDto);
    Optional<Client> ovaTopUp(ClientOvaTopUpDto clientOvaTopUpDto);
    boolean isValidApiKey(String apiKey);
    Client getClientByApiKey(String apiKey);

//    Authentication getAuthentication(String apiKey);
}
