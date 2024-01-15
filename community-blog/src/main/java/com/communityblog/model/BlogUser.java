package com.communityblog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Data
public class BlogUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Size(min = 4, message = "Password must be at least 4 characters long")
    @Pattern.List({
            @Pattern(regexp = "^(?=.*[0-9]).+$", message = "Password must contain at least one digit"),
            @Pattern(regexp = "^(?=.*[A-Z]).+$", message = "Password must contain at least one uppercase letter")    })
    private String password;

    @Column(unique = true)
    @Email(message = "Invalid email format")
    private String email;

    /*@ElementCollection
    private List<Role> roles;*/

    private String roles;


    /*public BlogUser(String username, String password, String email, List<Role> roles) {
    }*/

    public BlogUser(String username, String password, String email, String roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }
}
