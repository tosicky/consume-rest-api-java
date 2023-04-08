package com.example.consumerestapijava.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignupRequest {

    private String username;

    private String email;

    private String password;

    private Set<String> roles;
}
