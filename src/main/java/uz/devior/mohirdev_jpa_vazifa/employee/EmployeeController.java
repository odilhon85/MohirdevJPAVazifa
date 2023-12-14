package uz.devior.mohirdev_jpa_vazifa.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeDTO){
        ApplicationResponse<?> response = employeeService.addEmployee(employeeDTO);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @PutMapping
    public ResponseEntity<?> editEmployee(@RequestParam Long id,
                                          @RequestBody EmployeeDTO employeeDTO){
        ApplicationResponse<?> response = employeeService.editEmployee(id, employeeDTO);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployee(){
        ApplicationResponse<?> allEmployee = employeeService.getAllEmployee();
        return ResponseEntity.status(allEmployee.isSuccess()?200:404).body(allEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllEmployee(@PathVariable Long id){
        ApplicationResponse<?> employee = employeeService.getEmployee(id);
        return ResponseEntity.status(employee.isSuccess()?200:404).body(employee);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployee(@RequestParam Long id){
        ApplicationResponse<?> response = employeeService.delete(id);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping("/stat/by-department")
    public ResponseEntity<?> getAllByDepartment(){
        ApplicationResponse<?> allEmployee = employeeService.getAllByDepartment();
        return ResponseEntity.status(allEmployee.isSuccess()?200:404).body(allEmployee);
    }

    @GetMapping("/stat/by-age")
    public ResponseEntity<?> getAllByAge(@RequestParam Optional<Boolean> ascending){
        Sort sort = Sort.by(ascending.orElse(true)?Sort.Direction.ASC:Sort.Direction.DESC, "age");
        ApplicationResponse<?> allEmployee = employeeService.getAllByAge(sort);
        return ResponseEntity.status(allEmployee.isSuccess()?200:404).body(allEmployee);
    }

    @GetMapping("/stat/pagination")
    public ResponseEntity<?> getAllPagination(
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize){
        Pageable pageable = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(20));
        ApplicationResponse<?> allEmployee = employeeService.getAllPagination(pageable);
        return ResponseEntity.status(allEmployee.isSuccess()?200:404).body(allEmployee);
    }

    @GetMapping("/stat/summa")
    public ResponseEntity<?> getAllSumma(){
        ApplicationResponse<?> allEmployee = employeeService.getAllSumma();
        return ResponseEntity.status(allEmployee.isSuccess()?200:404).body(allEmployee);
    }
}
