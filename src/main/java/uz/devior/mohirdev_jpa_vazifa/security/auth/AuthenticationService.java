package uz.devior.mohirdev_jpa_vazifa.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import uz.devior.mohirdev_jpa_vazifa.employee.EmployeeRepository;
import uz.devior.mohirdev_jpa_vazifa.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
//    public AuthenticationResponse register(Employee employee) {
//        var user = Employee
//                .builder()
//                .username(employee.getUsername())
//                .password(passwordEncoder.encode(employee.getPassword()))
//                .role(Role.EMPLOYEE)//TODO dynamic role
//                .build();
//        userRepository.save(user);
//
//        var token = jwtService.generateToken(user);
//        return AuthenticationResponse
//                .builder()
//                .token(token)
//                .build();
//    }

    public AuthenticationResponse authenticate(AuthenticationDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        var user = userRepository.findByPassportId(dto.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

//    public AuthenticationResponse registerAdmin(RegisterRequest request) {
//        var user = UserEntity
//                .builder()
//                .username(request.getUsername())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.ADMIN)
//                .build();
//        userRepository.save(user);
//
//        var token = jwtService.generateToken(user);
//        return AuthenticationResponse
//                .builder()
//                .token(token)
//                .build();
//    }
}
