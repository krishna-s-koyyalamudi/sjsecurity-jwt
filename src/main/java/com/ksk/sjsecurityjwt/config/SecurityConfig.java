package com.ksk.sjsecurityjwt.config;

import com.ksk.sjsecurityjwt.service.UserInfoDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails admin = User.withUsername("Krishna")
//                .password(passwordEncoder.encode("Password1"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("Sumanth")
//                .password(passwordEncoder.encode("Password2"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
        return new UserInfoDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/products/welcome").permitAll() // Allow access to "/products/welcome" without authentication
                        .requestMatchers("/users").permitAll() // Allow access to "/users" without authentication
                        .requestMatchers("/products/**").authenticated()  // Require authentication for all other "/products/**" endpoints
                )
                .formLogin(Customizer.withDefaults()); // Enable form login with default settings

        return http.build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(customizer -> customizer.disable());
//        http.authorizeHttpRequests(request-> request
//                .requestMatchers("/products/welcome").permitAll()
//                .requestMatchers("/products/**").authenticated());
//        http.formLogin(Customizer.withDefaults());
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        return http.csrf().disable().authorizeHttpRequests().requestMatchers("/products/welcome").permitAll()
//                .and().authorizeHttpRequests().requestMatchers("/products/**").authenticated()
//                .and().formLogin().and().build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
