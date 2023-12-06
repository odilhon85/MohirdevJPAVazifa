package uz.devior.mohirdev_jpa_vazifa.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    DIRECTOR("Direktor", Set.of(
            Permission.DIRECTOR_CREATE,
            Permission.DIRECTOR_READ,
            Permission.DIRECTOR_UPDATE,
            Permission.DIRECTOR_DELETE
    )),
    DEPARTMENT_HEAD("Bo'lim boshlig'i", Set.of(
            Permission.DEPARTMENT_HEAD_CREATE,
            Permission.DEPARTMENT_HEAD_READ,
            Permission.DEPARTMENT_HEAD_UPDATE,
            Permission.DEPARTMENT_HEAD_DELETE
    )),
    EMPLOYEE("Xodim", Set.of(
            Permission.EMPLOYEE_CREATE,
            Permission.EMPLOYEE_READ,
            Permission.EMPLOYEE_UPDATE
    ));

    @Getter
    private final String role;
    @Getter
    private final Set<Permission> permissions;


    @JsonCreator
    public static Role getPositionFromCode(String value) {

        for (Role role : Role.values()) {
            if (role.getRole().equals(value)) {
                return role;
            }
        }

        return null;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
