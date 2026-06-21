package cibertec.pe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cibertec.pe.model.*;
import cibertec.pe.repository.*;

@Service
public class AuthService {
	
    @Autowired
    private IUsuarioCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UsuarioCredential credential) {
        try{
            credential.setPassword(passwordEncoder.encode(credential.getPassword()));
            repository.save(credential);
            return "Usuario registrado.";
        } catch (Exception e) {            
            System.out.println("Usuario no registrado:".concat(e.getMessage()));
            
            return "Usuario No registrado.";
            
        }
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
