package com.project.benchmark.algorithm.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
