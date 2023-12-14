package uz.devior.mohirdev_jpa_vazifa.customer;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {


    private String firstName;
    private String lastName;
    private String passportSerial;
    private Long passportNumber;
    private Long passportId;
    private String address;
    private Long employeeId;
    private Boolean archived;
}
