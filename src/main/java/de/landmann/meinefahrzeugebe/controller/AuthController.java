package de.landmann.meinefahrzeugebe.controller;

import de.landmann.meinefahrzeugebe.dto.AuthResponse;
import de.landmann.meinefahrzeugebe.dto.LoginRequest;
import de.landmann.meinefahrzeugebe.dto.RegisterRequest;
import de.landmann.meinefahrzeugebe.entity.User;
import de.landmann.meinefahrzeugebe.service.JwtService;
import de.landmann.meinefahrzeugebe.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(user);
            
            // Update last login time
            userService.updateLastLogin(user.getUsername());

            AuthResponse response = new AuthResponse(
                    jwtToken,
                    user.getUsername(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse("Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.createUser(
                    request.getUsername(),
                    request.getPassword(),
                    request.getEmail(),
                    request.getFirstName(),
                    request.getLastName()
            );

            String jwtToken = jwtService.generateToken(user);

            AuthResponse response = new AuthResponse(
                    jwtToken,
                    user.getUsername(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole()
            );

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(e.getMessage()));
        }
    }
}
