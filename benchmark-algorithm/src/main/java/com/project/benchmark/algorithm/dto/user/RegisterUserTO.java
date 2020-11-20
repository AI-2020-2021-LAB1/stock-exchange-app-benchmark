package com.project.benchmark.algorithm.dto.user;

import lombok.Data;

@Data
public class RegisterUserTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
