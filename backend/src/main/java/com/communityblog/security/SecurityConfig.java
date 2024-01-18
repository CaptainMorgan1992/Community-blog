package com.communityblog.security;

import com.communityblog.filter.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;


@Configuration
    public class SecurityConfig {
        @Bean
        public static PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
            return configuration.getAuthenticationManager();
        }

            @Bean
            SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

                CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
                HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES));
                requestHandler.setCsrfRequestAttributeName("_csrf");
                http.securityContext((context) -> context.requireExplicitSave(false))
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                        .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowCredentials(true);
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setMaxAge(3600L);
                            return config;
                        }))
                        .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                                .ignoringRequestMatchers("/register", "/hello", "/api", "/api/**")
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                        .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/api/register", "/api/login", "/api/blogpost").permitAll()
                                .requestMatchers("/api/blogpost/all", "/api/blogpost/{id}", "/api/blogpost/delete/{id}").permitAll()
                                .requestMatchers("/create-blogpost", "/api/blogpost/create").authenticated())
                        .formLogin(Customizer.withDefaults())
                        .httpBasic(Customizer.withDefaults())

                        .logout((logout) ->
                                logout.logoutUrl("/api/logout")
                                        .invalidateHttpSession(true)
                                        .deleteCookies("JSESSION")
                                        .clearAuthentication(true)
                                        .logoutSuccessUrl("/login?logout")
                                        .permitAll());



                                return http.build();
            }

}

/*
    .invalidateHttpSession(true)
                                        .deleteCookies("JSESSION")
                                        .clearAuthentication(true)
                                        .logoutSuccessUrl("/login?logout")
                                        .permitAll());  // Permit all for logout URL
 */

