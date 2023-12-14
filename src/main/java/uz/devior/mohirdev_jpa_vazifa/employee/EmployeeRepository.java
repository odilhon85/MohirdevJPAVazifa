package uz.devior.mohirdev_jpa_vazifa.employee;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.devior.mohirdev_jpa_vazifa.department.Department;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByPassportId(Long passportId);

    Optional<Employee> findByPassportId(Long passportId);

    Integer countEmployeeByDepartment(Department department);

    @Query("select sum(e.salary) from Employee e")
    Double sumOfSalary();
}
