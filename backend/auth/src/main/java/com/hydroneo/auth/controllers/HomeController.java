package com.hydroneo.auth.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController 
public class HomeController {
    
    private final OAuth2AuthorizedClientService authorizedClientService;
    
    // Constructor injection for OAuth2AuthorizedClientService
    public HomeController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }
    
    @GetMapping("/")
    public String home(){
        return "Hello, Home";
    }

    @GetMapping("/auth")
    public ResponseEntity<Map<String, String>> secured(@AuthenticationPrincipal OAuth2User principal) {
        // Use HashMap to handle null values
        Map<String, String> userInfo = new HashMap<>();
        
        // Get current authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        String token = null;
        
        // Extract token if authentication is OAuth2AuthenticationToken
        if (authentication instanceof OAuth2AuthenticationToken oauth2Authentication) {
            String clientRegistrationId = oauth2Authentication.getAuthorizedClientRegistrationId();
            
            // Get the OAuth2AuthorizedClient
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                clientRegistrationId,
                oauth2Authentication.getName()
            );
            
            // Get the access token from the client
            if (client != null && client.getAccessToken() != null) {
                token = client.getAccessToken().getTokenValue();
                userInfo.put("token", token);
                userInfo.put("tokenType", client.getAccessToken().getTokenType().getValue());
                userInfo.put("expiresAt", client.getAccessToken().getExpiresAt() != null ? 
                    client.getAccessToken().getExpiresAt().toString() : "unknown");
            }
            
            // Get email using provider-specific attributes
            email = principal.getAttribute("email");
            if (email != null) {
                userInfo.put("email", email);
            }
        }
        
        // Add other user attributes
        if (principal.getAttribute("sub") != null) {
            userInfo.put("userId", principal.getAttribute("sub"));
        }
        
        if (principal.getAttribute("name") != null) {
            userInfo.put("name", principal.getAttribute("name"));
        }
        
        // Add login attribute for GitHub specifically
        if (principal.getAttribute("login") != null) {
            userInfo.put("login", principal.getAttribute("login"));
        }
        
        // Add avatar URL for GitHub
        if (principal.getAttribute("avatar_url") != null) {
            userInfo.put("avatar_url", principal.getAttribute("avatar_url"));
        }
        
        return ResponseEntity.ok(userInfo);
    }
}