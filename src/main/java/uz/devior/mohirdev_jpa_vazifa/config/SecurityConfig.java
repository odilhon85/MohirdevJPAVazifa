package uz.devior.mohirdev_jpa_vazifa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.devior.mohirdev_jpa_vazifa.security.jwt.JwtAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static uz.devior.mohirdev_jpa_vazifa.shared.Permission.*;
import static uz.devior.mohirdev_jpa_vazifa.shared.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "api/auth/**").permitAll()

                        .requestMatchers(POST,"api/v1/employee/**").hasAnyAuthority(DIRECTOR_CREATE.name(), DEPARTMENT_HEAD_CREATE.name(),EMPLOYEE_CREATE.name())
                        .requestMatchers(PUT,"api/v1/employee/**").hasAnyAuthority(DIRECTOR_UPDATE.name(), DEPARTMENT_HEAD_UPDATE.name(),EMPLOYEE_UPDATE.name())
                        .requestMatchers(GET,"api/v1/employee/*").hasAnyAuthority(DIRECTOR_READ.name(), DEPARTMENT_HEAD_READ.name(),EMPLOYEE_READ.name())
                        .requestMatchers(GET,"api/v1/employee/stat/**").hasAnyAuthority(DIRECTOR_READ.name(), DEPARTMENT_HEAD_READ.name())
                        .requestMatchers(DELETE,"api/v1/employee/**").hasAuthority(DIRECTOR_DELETE.name())

                        .requestMatchers(POST,"api/v1/customer/**").hasAnyAuthority(DIRECTOR_CREATE.name(), DEPARTMENT_HEAD_CREATE.name(),EMPLOYEE_CREATE.name())
                        .requestMatchers(PUT,"api/v1/customer/**").hasAnyAuthority(DIRECTOR_UPDATE.name(), DEPARTMENT_HEAD_UPDATE.name(),EMPLOYEE_UPDATE.name())
                        .requestMatchers(PATCH,"api/v1/customer/**").hasAnyAuthority(DIRECTOR_UPDATE.name(), DEPARTMENT_HEAD_UPDATE.name(),EMPLOYEE_UPDATE.name())
                        .requestMatchers(GET,"api/v1/customer/*").hasAnyAuthority(DIRECTOR_READ.name(), DEPARTMENT_HEAD_READ.name(),EMPLOYEE_READ.name())
                        .requestMatchers(GET,"api/v1/customer/stat/**").hasAnyAuthority(DIRECTOR_READ.name(), DEPARTMENT_HEAD_READ.name())
                        .requestMatchers(DELETE,"api/v1/customer/**").hasAnyAuthority(DIRECTOR_DELETE.name(), DEPARTMENT_HEAD_DELETE.name())

                        .requestMatchers(POST,"api/v1/expense/**").hasAnyAuthority(DIRECTOR_CREATE.name(), DEPARTMENT_HEAD_CREATE.name(),EMPLOYEE_CREATE.name())
                        .requestMatchers(PUT,"api/v1/expense/**").hasAnyAuthority(DIRECTOR_UPDATE.name(), DEPARTMENT_HEAD_UPDATE.name(),EMPLOYEE_UPDATE.name())
                        .requestMatchers(GET,"api/v1/expense/*").hasAnyAuthority(DIRECTOR_READ.name(), DEPARTMENT_HEAD_READ.name(),EMPLOYEE_READ.name())
                        .requestMatchers(GET,"api/v1/expense/stat/**").hasAnyAuthority(DIRECTOR_READ.name(), DEPARTMENT_HEAD_READ.name())
                        .requestMatchers(DELETE,"api/v1/expense/**").hasAnyAuthority(DIRECTOR_DELETE.name(), DEPARTMENT_HEAD_DELETE.name())

                        .requestMatchers("api/v1/department/**").hasRole(DIRECTOR.name())

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

}
