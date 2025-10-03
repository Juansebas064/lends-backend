package com.app.lends_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String occupation;
    private String phone;
    private String email;
    private String role;
}
