package com.project.benchmark.algorithm.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsTO {
    private String email;
    private String firstName;
    private Integer id;
    private Boolean isActive;
    private String lastName;
    private Double money;
    private String role;
    private String tag;
}
