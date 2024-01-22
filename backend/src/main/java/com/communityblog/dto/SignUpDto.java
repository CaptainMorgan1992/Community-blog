package com.communityblog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpDto {

    private String name;
    @Size(min = 5, message = "Username must be at least 5 characters long")
    private String username;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 4, message = "Password must be at least 4 characters long")
    @Pattern.List({
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z]).+$", message = "Password must contain one digit and one uppercase letter")
    })
    private String password;

}
