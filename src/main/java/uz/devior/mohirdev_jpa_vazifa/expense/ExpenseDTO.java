package uz.devior.mohirdev_jpa_vazifa.expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDTO {

    private String type;
    private Double price;
    private Integer duration;
    private String startDate;
    private Long id;
}
