package uz.devior.mohirdev_jpa_vazifa.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import uz.devior.mohirdev_jpa_vazifa.employee.EmployeeRepository;
import uz.devior.mohirdev_jpa_vazifa.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final EmployeeRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        var user = userRepository.findByPassportId(dto.getUsername()).orElseThrow();
        log.info("User authenticated: "+dto.getUsername());
        var token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }
}
