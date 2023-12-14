package uz.devior.mohirdev_jpa_vazifa.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CustomerDTO dto){
        ApplicationResponse<?> response = customerService.addCustomer(dto);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestParam Integer id,
            @RequestBody CustomerDTO dto){
        ApplicationResponse<?> response = customerService.updateCustomer(id,dto);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam Long passportId){
        ApplicationResponse<?> response = customerService.getCustomer(passportId);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping("/by-employee")
    public ResponseEntity<?> getByEmployee(@RequestParam Long employeeId){
        ApplicationResponse<?> response = customerService.getByEmployeeCustomer(employeeId);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        ApplicationResponse<?> response = customerService.getAllCustomer();
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @PatchMapping("/archiving")
    public ResponseEntity<?> toArchive(
            @RequestParam Long passportId,
            @RequestParam Boolean archive){
        ApplicationResponse<?> response = customerService.toArchive(passportId, archive);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCustomer(@RequestParam Long id){
        ApplicationResponse<?> response = customerService.delete(id);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping("/stat/daily")
    public ResponseEntity<?> getDailyCustomers(@RequestParam String date){
        LocalDateTime startDay = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay();
        LocalDateTime endDay = startDay.plusDays(1);
        ApplicationResponse<?> response = customerService.getDailyCustomers(startDay, endDay);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }
    @GetMapping("/stat/best-employee")
    public ResponseEntity<?> getBestEmployee(){
        ApplicationResponse<?> response = customerService.getBestEmployee();
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping("/stat/best-three")
    public ResponseEntity<?> getBestThree(){
        ApplicationResponse<?> response = customerService.getBestThree();
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping("/stat/last-month")
    public ResponseEntity<?> getLastMonth(){
        ApplicationResponse<?> response = customerService.getLastMonth();
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping("/stat/top-day")
    public ResponseEntity<?> getTopDay(){
        ApplicationResponse<?> response = customerService.getTopDay();
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }
}
