package com.machines.xrut.controller;


import com.machines.xrut.entity.Client;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {


    @GetMapping("/test-user")
    @PreAuthorize("hasRole('USER') ")
    public String testUser(){
        return "Testing User";
    }


    @GetMapping("/test-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdmin(){
        return "Testing Admin";
    }


    @GetMapping("/test-log")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpEntity<String> testlog() {
        // Get the authenticated user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // This gets the username

        // Log the username
        Logger logger = LoggerFactory.getLogger(TestController.class);
        logger.info("User {} accessed /test-log route", username);
        logger.debug("Log to file");

        return ResponseEntity.ok().body("testlog");
    }


    @GetMapping("/client/test-api-key")
    public ResponseEntity<Map<String, Object>> testApiKey(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if (authentication != null) {
            log.debug("Authentication class: {}", authentication.getClass().getName());
            log.debug("Principal class: {}", authentication.getPrincipal().getClass().getName());
            log.debug("Authorities: {}", authentication.getAuthorities());

            response.put("authenticated", true);
            response.put("principal", authentication.getPrincipal());
            response.put("authorities", authentication.getAuthorities());

            return ResponseEntity.ok(response);
        }

        response.put("authenticated", false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    @GetMapping("/debug")
    public ResponseEntity<?> debugEndpoint(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> debugInfo = new HashMap<>();

        debugInfo.put("apiKey", request.getHeader("X-API-KEY"));
        debugInfo.put("isAuthenticated", auth != null && auth.isAuthenticated());
        if (auth != null) {
            debugInfo.put("authorities", auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            debugInfo.put("principal", auth.getPrincipal().toString());
        }

        return ResponseEntity.ok(debugInfo);
    }
}
