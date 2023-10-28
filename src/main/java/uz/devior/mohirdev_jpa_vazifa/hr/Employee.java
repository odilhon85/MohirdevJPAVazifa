package uz.devior.mohirdev_jpa_vazifa.hr;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.devior.mohirdev_jpa_vazifa.shared.Department;
import uz.devior.mohirdev_jpa_vazifa.shared.Position;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String passportSerial;

    @Column(nullable = false)
    private Integer passportNumber;

    @Column(nullable = false, unique = true)
    private Long passportId;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private Integer salary;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Position position;









}
