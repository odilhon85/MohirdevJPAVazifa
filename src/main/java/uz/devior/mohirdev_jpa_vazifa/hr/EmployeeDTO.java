package uz.devior.mohirdev_jpa_vazifa.hr;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.devior.mohirdev_jpa_vazifa.shared.Department;
import uz.devior.mohirdev_jpa_vazifa.shared.Position;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private String firstName;

    private String lastName;

    private String middleName;

    private Integer age;

    private String passportSerial;

    private Integer passportNumber;

    private Long passportId;

    private String nationality;

    private Integer salary;

    private String address;

    @Enumerated
    private Department department;

    @Enumerated
    private Position position;
}
