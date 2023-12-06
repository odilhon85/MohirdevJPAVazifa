package uz.devior.mohirdev_jpa_vazifa.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import uz.devior.mohirdev_jpa_vazifa.employee.Employee;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String passportSerial;

    @Column(nullable = false)
    private Long passportNumber;

    @Column(nullable = false, unique = true)
    private Long passportId;

    @Column(nullable = false)
    private String address;


    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    @CreatedDate
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
