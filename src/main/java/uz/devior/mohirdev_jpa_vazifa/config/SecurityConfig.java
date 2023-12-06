package uz.devior.mohirdev_jpa_vazifa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.devior.mohirdev_jpa_vazifa.security.jwt.JwtAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static uz.devior.mohirdev_jpa_vazifa.shared.Permission.DEPARTMENT_HEAD_DELETE;
import static uz.devior.mohirdev_jpa_vazifa.shared.Permission.DIRECTOR_DELETE;
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
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "api/auth/**").permitAll()

                                .requestMatchers(
                                        "api/v1/employee/**",
                                        "api/v1/customer/**"
                                ).hasAnyRole(DIRECTOR.name(), DEPARTMENT_HEAD.name(), EMPLOYEE.name())

                                .requestMatchers("api/v1/department/**")
                                .hasRole(DIRECTOR.name())

                                .requestMatchers(DELETE, "api/v1/employee/**")
                                .hasAuthority(DIRECTOR_DELETE.name())

                                .requestMatchers(DELETE, "api/v1/customer/**")
                                .hasAnyAuthority(DIRECTOR_DELETE.name(), DEPARTMENT_HEAD_DELETE.name())


//                        .requestMatchers("api/v1/admin/**").hasRole(ADMIN.name())
//                        .requestMatchers(GET,"api/v1/admin/**").hasAuthority(ADMIN_READ.name())
//                        .requestMatchers(POST,"api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
//                        .requestMatchers(PUT,"api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
//                        .requestMatchers(DELETE,"api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
//
//                        .requestMatchers("api/v1/user/**").hasAnyRole(ADMIN.name(), USER.name())
//                        .requestMatchers(POST,"api/v1/user/**").hasAnyAuthority(USER_CREATE.name())
//                        .requestMatchers(PUT,"api/v1/user/**").hasAnyAuthority(USER_UPDATE.name())
//                        .requestMatchers(DELETE,"api/v1/user/**").hasAnyAuthority(USER_DELETE.name())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

}
