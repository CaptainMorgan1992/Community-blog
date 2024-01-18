package com.communityblog.security;

import com.communityblog.filter.CsrfCookieFilter;
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
                        .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                                .ignoringRequestMatchers("/register", "/hello", "/api", "/api/**")
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                        .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/api/**", "/register", "/login", "/blogpost").permitAll()
                                .requestMatchers("/api/blogpost**", "/all", "/{id}", "delete/{id}").permitAll()
                                .requestMatchers("/create-blogpost", "api/blogpost/create/{id}").authenticated())
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

