package com.rojas.dev.tvc.controller;

import com.rojas.dev.tvc.Repository.UsuarioRepository;
import com.rojas.dev.tvc.auth.AuthRequest;
import com.rojas.dev.tvc.auth.AuthResponse;
import com.rojas.dev.tvc.auth.RegisterRequest;
import com.rojas.dev.tvc.entity.Usuario;
import com.rojas.dev.tvc.security.JwtService;
import com.rojas.dev.tvc.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    // ✅ Registro
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .contraseña(passwordEncoder.encode(request.getContraseña()))
                .identificacion(request.getIdentificacion())
                .telefono(request.getTelefono())
                .tipoUsuario(request.getTipoUsuario())
                .municipio(request.getMunicipio())
                .departamento(request.getDepartamento())
                .fechaCreacion(java.time.LocalDate.now().toString())
                .build();

        Usuario newUser = usuarioRepository.save(usuario);

        String token = jwtService.generateAccessToken(newUser.getCorreo());
        String refreshToken = jwtService.generateRefreshToken(usuario.getCorreo());

        return ResponseEntity.ok(new AuthResponse(usuario.getIdUsuario(),token, refreshToken));
    }

    // ✅ Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContraseña())
        );

        String token = jwtService.generateAccessToken(request.getCorreo());
        String refreshToken = jwtService.generateRefreshToken(request.getCorreo());
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(request.getCorreo());
        return ResponseEntity.ok(new AuthResponse(usuario.get().getIdUsuario(),token, refreshToken));
    }

    // ✅ Refresh token
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam String refreshToken) {
        String correo = jwtService.extractUsername(refreshToken);

        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
        if (usuario.isEmpty() || !jwtService.isTokenValid(refreshToken, correo)) {
            return ResponseEntity.status(403).build();
        }

        System.out.println(refreshToken);

        String newRefreshToken = jwtService.generateRefreshToken(correo);
        String newAccessToken = jwtService.generateAccessToken(correo);
        return ResponseEntity.ok(new AuthResponse(usuario.get().getIdUsuario(),newAccessToken, newRefreshToken));
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> getCurrentUser(@RequestParam String authentication) {
        String correo = jwtService.extractUsername(authentication);
        Usuario user = usuarioService.buscarPorCorreo(correo);
        return ResponseEntity.ok(user);
    }

}

