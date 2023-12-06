package uz.devior.mohirdev_jpa_vazifa.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByPassportId(Long passportId);

    Customer findByPassportId(Long passportId);
}
