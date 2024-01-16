package com.communityblog.controller;

import com.communityblog.JWT.JwtTokenProvider;
import com.communityblog.dto.LoginRequest;
import com.communityblog.dto.RegisterSuccess;
import com.communityblog.model.BlogUser;
import com.communityblog.service.BlogUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BlogUserController {

    private final BlogUserService blogUserService;
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Builder
    private record UserDTO(String username, String password, String role, String email ){};

    @Autowired
    public BlogUserController(BlogUserService blogUserService,
                              JwtTokenProvider jwtTokenProvider,
                              UserDetailsService userDetailsService,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager) {
        this.blogUserService = blogUserService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterSuccess> registerUser(@Valid @RequestBody UserDTO userDTO) {
        var result = blogUserService.register(userDTO.username(), userDTO.password(), userDTO.email());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        if(!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())){
            return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword());

        authenticationManager.authenticate(authentication);

        if(authentication.isAuthenticated()) {
            String jwtToken = jwtTokenProvider.generateToken((BlogUser) userDetails);

            session.setAttribute("jwtToken", jwtToken);
            return ResponseEntity.ok(jwtToken);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @GetMapping("/success")
    public String success(){
        return "Register successful!";
    }

    @GetMapping("/hello")
    public String getMessage(){
        return "Hello Lina!";
    }


}
