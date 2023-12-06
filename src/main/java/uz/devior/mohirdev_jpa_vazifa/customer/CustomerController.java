package uz.devior.mohirdev_jpa_vazifa.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

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

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        ApplicationResponse<?> response = customerService.getAllCustomer();
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }
}
