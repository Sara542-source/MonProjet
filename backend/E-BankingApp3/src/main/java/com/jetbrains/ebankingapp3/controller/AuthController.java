package com.jetbrains.ebankingapp3.controller;

import com.jetbrains.ebankingapp3.auth.AuthService;
import com.jetbrains.ebankingapp3.model.Client;
import com.jetbrains.ebankingapp3.service.ClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {
    private final AuthService authService;
    private final ClientService clientService;
    public AuthController(AuthService authService, ClientService clientService) {
        this.authService = authService;
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("login") String login,
                                   @RequestParam("password") String password) {
        Client client = clientService.login(login, password);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String sessionId = authService.authenticate(login, password);
        if (sessionId != null) {

            // remove comments to send sms

            /*boolean codeSent = clientService.sendCodeToClient(client.getClientId());
            if (!codeSent) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to send code to client.");
            }*/
            ResponseCookie cookie = ResponseCookie.from("SESSIONID", sessionId)
                    .httpOnly(true)
                    .secure(false) // Set to true in production with HTTPS
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60) // 7 days
                    .sameSite("Lax")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "SESSIONID", defaultValue = "") String sessionId) {
        if (!sessionId.isEmpty()) {
            authService.logout(sessionId);

            ResponseCookie cookie = ResponseCookie.from("SESSIONID", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(0)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/current-user")
    public ResponseEntity<Client> getCurrentUser(@CookieValue(name = "SESSIONID", defaultValue = "") String sessionId) {
        if (sessionId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Client client = authService.getClientFromSession(sessionId);
        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}