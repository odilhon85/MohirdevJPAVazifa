package uz.devior.mohirdev_jpa_vazifa.department;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestBody DepartmentDTO dto){
        ApplicationResponse<?> response = departmentService.add(dto);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateDepartment(
            @RequestParam Long id,
            @RequestBody DepartmentDTO dto){
        ApplicationResponse<?> response = departmentService.update(id, dto);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getDepartment(){
        ApplicationResponse<?> response = departmentService.get();
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> updateDepartment(@RequestParam Long id){
        ApplicationResponse<?> response = departmentService.delete(id);
        return ResponseEntity.status(response.isSuccess()?200:404).body(response);
    }
}
