package br.com.github.ljeronimodarocha.vendas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

        private final JwtAuthConverter jwtAuthConverter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(requests -> requests
                                                .anyRequest()
                                                .authenticated());

                http
                                .oauth2ResourceServer(server -> server
                                                .jwt(j -> j.jwtAuthenticationConverter(jwtAuthConverter)));

                http
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

                return http.build();
        }
}