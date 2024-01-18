package com.communityblog.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import java.util.Set;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Size(min = 4, message = "Password must be at least 4 characters long")
    @Pattern.List({
    @Pattern(regexp = "^(?=.*[0-9]).+$", message = "Password must contain at least one digit"),
    @Pattern(regexp = "^(?=.*[A-Z]).+$", message = "Password must contain at least one uppercase letter")    })
    private String password;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Role> roles;

}
