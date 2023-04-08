package com.example.consumerestapijava.controllers;

import com.example.consumerestapijava.models.ERole;
import com.example.consumerestapijava.models.Role;
import com.example.consumerestapijava.models.User;
import com.example.consumerestapijava.payload.JwtResponse;
import com.example.consumerestapijava.payload.LoginRequest;
import com.example.consumerestapijava.payload.MessageResponse;
import com.example.consumerestapijava.payload.SignupRequest;
import com.example.consumerestapijava.repository.RoleRepository;
import com.example.consumerestapijava.repository.UserRepository;
import com.example.consumerestapijava.security.jwt.JwtUtils;
import com.example.consumerestapijava.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(JwtResponse.builder()
                .token(jwt)
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .username(userDetails.getUsername())
                .roles(roles).build());

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody
                                          SignupRequest signUpRequest) {
        try {
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }

            User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .email(signUpRequest.getEmail())
                    .password(encoder.encode(signUpRequest.getPassword()))
                    .build();
            Set<String> strRoles = signUpRequest.getRoles();
            Set<Role> roles = new HashSet<>();
            List<Role> appRoles = roleRepository.findAll();
            if (appRoles == null || appRoles.isEmpty()) {
                throw new RuntimeException("No role has been configured. Kindly contact administrator");
            }
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.stream().forEach(r -> {
                    roles.add(appRoles.stream().filter(rol -> rol.getName().name().equals(r)).findFirst().orElseThrow(() -> new RuntimeException("Role name provided not valid!!")));
                });
            }
            user.setRoles(roles);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
