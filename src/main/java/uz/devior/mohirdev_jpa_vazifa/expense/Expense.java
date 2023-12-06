package uz.devior.mohirdev_jpa_vazifa.expense;

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
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Double price;

    private Integer duration;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    @CreatedDate
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
