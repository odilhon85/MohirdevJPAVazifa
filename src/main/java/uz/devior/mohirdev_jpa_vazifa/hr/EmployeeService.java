package uz.devior.mohirdev_jpa_vazifa.hr;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    public ApplicationResponse<?> addEmployee(EmployeeDTO employeeDTO) {

        boolean existedByPassportId = employeeRepository.existsByPassportId(employeeDTO.getPassportId());
        if(existedByPassportId){
            return ApplicationResponse.builder()
                    .message("Employee with this passportId is already exists")
                    .success(false)
                    .build();
        }

        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setMiddleName(employeeDTO.getMiddleName());
        employee.setAge(employeeDTO.getAge());
        employee.setPassportSerial(employeeDTO.getPassportSerial());
        employee.setPassportNumber(employeeDTO.getPassportNumber());
        employee.setPassportId(employeeDTO.getPassportId());
        employee.setNationality(employeeDTO.getNationality());
        employee.setSalary(employeeDTO.getSalary());
        employee.setAddress(employeeDTO.getAddress());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setPosition(employeeDTO.getPosition());
        employeeRepository.save(employee);
        return ApplicationResponse.builder()
                .message("New employee saved")
                .success(true)
                .build();
    }

    public ApplicationResponse<?> editEmployee(Long employeeId, EmployeeDTO employeeDTO) {

        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isEmpty()){
            return ApplicationResponse.builder()
                    .message("Employee with this ID is not exist")
                    .success(false)
                    .build();
        }

        boolean existedByPassportId = employeeRepository.existsByPassportId(employeeDTO.getPassportId());
        if(existedByPassportId && !employee.get().getPassportId().equals(employeeDTO.getPassportId())){
            return ApplicationResponse.builder()
                    .message("Employee with this passportId is already exists")
                    .success(false)
                    .build();
        }

        Employee updatedEmployee = employee.get();
        updatedEmployee.setFirstName(employeeDTO.getFirstName());
        updatedEmployee.setLastName(employeeDTO.getLastName());
        updatedEmployee.setMiddleName(employeeDTO.getMiddleName());
        updatedEmployee.setAge(employeeDTO.getAge());
        updatedEmployee.setPassportSerial(employeeDTO.getPassportSerial());
        updatedEmployee.setPassportNumber(employeeDTO.getPassportNumber());
        updatedEmployee.setPassportId(employeeDTO.getPassportId());
        updatedEmployee.setNationality(employeeDTO.getNationality());
        updatedEmployee.setSalary(employeeDTO.getSalary());
        updatedEmployee.setAddress(employeeDTO.getAddress());
        updatedEmployee.setDepartment(employeeDTO.getDepartment());
        updatedEmployee.setPosition(employeeDTO.getPosition());
        employeeRepository.save(updatedEmployee);
        return ApplicationResponse.builder()
                .message("Employee updated")
                .success(true)
                .build();
    }

    public ApplicationResponse<?> getAllEmployee() {

        List<Employee> all = employeeRepository.findAll();
        if(all.isEmpty()){
            return ApplicationResponse.builder()
                    .message("There is not any employee")
                    .data(Map.of("Employees",all))
                    .success(false)
                    .build();
        }
        return ApplicationResponse.builder()
                .message("Ok")
                .data(Map.of("Employees",all))
                .success(true)
                .build();
    }

    public ApplicationResponse<?> getEmployee(Long id) {

        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()){
            return ApplicationResponse.builder()
                    .message("Employee with this ID is not exist")
                    .success(false)
                    .build();
        }
        return ApplicationResponse.builder()
                .message("Ok")
                .data(Map.of("Employee",employee))
                .success(true)
                .build();
    }

    public ApplicationResponse<?> delete(Long id) {

        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isEmpty()){
            return ApplicationResponse.builder()
                    .message("Employee with this ID is not exist")
                    .success(false)
                    .build();
        }
        employeeRepository.delete(employee.get());
        return ApplicationResponse.builder()
                .message("Employee with ID:"+id+" was successfully deleted")
                .success(true)
                .build();
    }
}
