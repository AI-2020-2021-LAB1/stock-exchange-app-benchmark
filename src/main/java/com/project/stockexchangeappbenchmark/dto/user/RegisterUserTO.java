package com.project.stockexchangeappbenchmark.dto.user;

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
