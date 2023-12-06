package uz.devior.mohirdev_jpa_vazifa.employee;

import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.devior.mohirdev_jpa_vazifa.department.Department;
import uz.devior.mohirdev_jpa_vazifa.shared.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private String firstName;

    private String lastName;

    private String middleName;

    private String password;

    private Integer age;

    private String passportSerial;

    private Long passportNumber;

    private Long passportId;

    private String nationality;

    private Integer salary;

    private String address;

    private String department;

    @Enumerated
    private Role role;
}
