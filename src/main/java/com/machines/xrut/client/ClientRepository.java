package com.machines.xrut.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
  Optional<Client> findByEmail(String email);
  Optional<Client> findByClientId(String clientId);
  Optional<Client> findByApiKey(String apiKey);
  boolean existsByApiKey(String apiKey);
}