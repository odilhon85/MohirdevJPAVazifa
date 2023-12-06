package uz.devior.mohirdev_jpa_vazifa.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {

    private Long username;

    private String password;
}
