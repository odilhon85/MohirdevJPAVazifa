package uz.devior.mohirdev_jpa_vazifa.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.devior.mohirdev_jpa_vazifa.employee.Employee;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByPassportId(Long passportId);

    Customer findByPassportId(Long passportId);

    List<Customer> findAllByEmployeeId(Long employeeId);

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.date >= :startDate AND c.date < :endDate")
    Long findAllByDateWithin(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT c.employee FROM Customer c " +
            "GROUP BY c.employee " +
            "ORDER BY COUNT(c.id) DESC")
    List<Employee> findBestEmployee();

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.date BETWEEN :startDate AND :endDate")
    Long countCustomersRegisteredLast30Days(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT DATE_TRUNC('day', c.date) AS registrationDay, COUNT(c) AS registrationCount " +
            "FROM Customer c " +
            "GROUP BY registrationDay " +
            "ORDER BY registrationCount DESC " +
            "LIMIT :pageSize",
            nativeQuery = true)
    List<Object[]> findDayWithMostRegistrations(@Param("pageSize") int pageSize);
}
