package de.landmann.meinefahrzeugebe.dto;

import de.landmann.meinefahrzeugebe.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private String token;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String message;
    
    // Constructor for successful authentication
    public AuthResponse(String token, String username, String email, String firstName, String lastName, Role role) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
    
    // Constructor for error messages
    public AuthResponse(String message) {
        this.message = message;
    }
}
