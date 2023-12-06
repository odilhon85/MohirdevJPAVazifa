package uz.devior.mohirdev_jpa_vazifa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.devior.mohirdev_jpa_vazifa.department.Department;
import uz.devior.mohirdev_jpa_vazifa.department.DepartmentRepository;
import uz.devior.mohirdev_jpa_vazifa.employee.Employee;
import uz.devior.mohirdev_jpa_vazifa.employee.EmployeeRepository;
import uz.devior.mohirdev_jpa_vazifa.shared.Role;

import static uz.devior.mohirdev_jpa_vazifa.shared.Role.DIRECTOR;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        initDepartments();
        initDirector();
    }

    private void initDepartments() {
        createDepartment("Xodimlarni boshqarish bo’limi");
        createDepartment("Mijozlar bilan ishlash bo’limi");
        createDepartment("Sotuv bo’limi");
    }

    private void createDepartment(String name) {
        if (!departmentRepository.existsByName(name)) {
            Department department = new Department();
            department.setName(name);
            departmentRepository.save(department);
        }
    }

    private void initDirector() {
        createEmployee(
                "Director", "Director", "Director",
                "$2a$10$k7qSQEo/HyDOxswoFR7Kru.MOUkxviSjJWMmUHcSWPCsdYXMYadPa",
                30, "AA", 123456l, 123456l, "Uzbek",
                100000, "Director Address", "Xodimlarni boshqarish bo’limi", DIRECTOR
        );
    }

    private void createEmployee(
            String firstName, String lastName, String middleName, String password,
            Integer age, String passportSerial, Long passportNumber, Long passportId,
            String nationality, Integer salary, String address,
            String department, Role role
    ) {
        Department newDepartment = departmentRepository.findByName(department)
                .orElseThrow(() -> new RuntimeException("Department not found with name: " + department));

        if (!employeeRepository.existsByPassportId(passportId)) {
            Employee employee = new Employee();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setMiddleName(middleName);
            employee.setPassword(password);
            employee.setAge(age);
            employee.setPassportSerial(passportSerial);
            employee.setPassportNumber(passportNumber);
            employee.setPassportId(passportId);
            employee.setUsername(passportId);
            employee.setNationality(nationality);
            employee.setSalary(salary);
            employee.setAddress(address);
            employee.setDepartment(newDepartment);
            employee.setRole(role);

            employeeRepository.save(employee);
        }
    }
}
