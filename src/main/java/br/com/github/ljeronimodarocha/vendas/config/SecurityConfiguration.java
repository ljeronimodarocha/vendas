package br.com.github.ljeronimodarocha.vendas.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.oauth2Client(withDefaults())
                .oauth2Login(login -> login
                        .tokenEndpoint()
                        .and()
                        .userInfoEndpoint());

        http
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/oauth2/**", "/login/**").permitAll()
                        .anyRequest()
                        .fullyAuthenticated())
                .logout(logout -> logout
                        .logoutSuccessUrl(
                                "http://localhost:7080/realms/vendas/protocol/openid-connect/logout?redirect_uri=http://localhost:8081/"));

        return http.build();
    }
}