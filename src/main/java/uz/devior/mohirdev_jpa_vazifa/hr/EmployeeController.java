package uz.devior.mohirdev_jpa_vazifa.hr;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeDTO){
        ApplicationResponse<?> response = employeeService.addEmployee(employeeDTO);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @PutMapping("/employee")
    public ResponseEntity<?> editEmployee(@RequestParam Long id,
                                          @RequestBody EmployeeDTO employeeDTO){
        ApplicationResponse<?> response = employeeService.editEmployee(id, employeeDTO);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping("/employee/all")
    public ResponseEntity<?> getAllEmployee(){
        ApplicationResponse<?> allEmployee = employeeService.getAllEmployee();
        return ResponseEntity.status(allEmployee.isSuccess()?200:404).body(allEmployee);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getAllEmployee(@PathVariable Long id){
        ApplicationResponse<?> employee = employeeService.getEmployee(id);
        return ResponseEntity.status(employee.isSuccess()?200:404).body(employee);
    }

    @DeleteMapping("/employee")
    public ResponseEntity<?> deleteEmployee(@RequestParam Long id){
        ApplicationResponse<?> response = employeeService.delete(id);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

}
