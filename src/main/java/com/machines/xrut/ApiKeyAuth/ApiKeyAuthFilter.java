package com.machines.xrut.ApiKeyAuth;

import com.machines.xrut.client.Client;
import com.machines.xrut.client.ClientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Autowired
    private ClientService clientService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String apiKey = request.getHeader("X-API-KEY");

        if (apiKey != null && !apiKey.isEmpty()) {
            if (clientService.isValidApiKey(apiKey)) {
                Client client = clientService.getClientByApiKey(apiKey);

                // Simply authenticate without roles
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        client,
                        null,
                        Collections.emptyList()  // No roles needed
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
                log.debug("API Key authentication successful for client: {}", client.getClientId());
            }
        }

        filterChain.doFilter(request, response);
    }
}