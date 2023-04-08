package com.example.consumerestapijava.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtResponse {
    private String token;
    private Long id;
    private String type;
    private String username;
    private String email;

    private List<String> roles;
}
