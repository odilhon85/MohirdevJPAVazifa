package uz.devior.mohirdev_jpa_vazifa.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByPassportId(Long passportId);

    Optional<Employee> findByPassportId(Long passportId);
}
