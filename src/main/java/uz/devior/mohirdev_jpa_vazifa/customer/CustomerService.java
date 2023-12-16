package uz.devior.mohirdev_jpa_vazifa.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.devior.mohirdev_jpa_vazifa.employee.Employee;
import uz.devior.mohirdev_jpa_vazifa.employee.EmployeeRepository;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    public ApplicationResponse<?> addCustomer(CustomerDTO dto) {
        if(customerRepository.existsByPassportId(dto.getPassportId())){
            return ApplicationResponse
                    .builder()
                    .message("Customer with this passport ID already exists")
                    .success(false)
                    .build();
        }
        Customer customer = Customer
                .builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .passportSerial(dto.getPassportSerial())
                .passportNumber(dto.getPassportNumber())
                .passportId(dto.getPassportId())
                .address(dto.getAddress())
                .archived(dto.getArchived())
                .build();

        Optional<Employee> employee = employeeRepository.findByPassportId(dto.getEmployeeId());
        customer.setEmployee(employee.get());
        customer.setDate(LocalDateTime.now());

        Customer saved = customerRepository.save(customer);
        log.info("POST: New customer added: "+employee.get().getPassportId());
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Saved customer", saved))
                .build();
    }

    public ApplicationResponse<?> updateCustomer(Integer id, CustomerDTO dto) {
        if(!customerRepository.existsByPassportId(dto.getPassportId())){
            return ApplicationResponse
                    .builder()
                    .message("Customer with this passport ID does not exist")
                    .success(false)
                    .build();
        }
        Customer customer = customerRepository.findByPassportId(dto.getPassportId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPassportSerial(dto.getPassportSerial());
        customer.setPassportNumber(dto.getPassportNumber());
        customer.setAddress(dto.getAddress());
        customer.setArchived(dto.getArchived());
        Customer saved = customerRepository.save(customer);
        log.info("UPDATE: Customer updated: "+saved.getPassportId());
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Updated customer", saved))
                .build();
    }

    public ApplicationResponse<?> getCustomer(Long id) {
        if(!customerRepository.existsByPassportId(id)){
            return ApplicationResponse
                    .builder()
                    .message("Customer with this passport ID does not exist")
                    .success(false)
                    .build();
        }

        Customer byPassportId = customerRepository.findByPassportId(id);
        log.info("GET: Get info about customer: "+byPassportId.getPassportId());
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Customer", byPassportId))
                .build();
    }

    public ApplicationResponse<?> getByEmployeeCustomer(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findByPassportId(employeeId);
        List<Customer> all = customerRepository.findAllByEmployeeId(employee.get().getId());
        log.info("GET: Get customers by Employee ID: "+employeeId);
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Employee customers", all))
                .build();
    }

    public ApplicationResponse<?> getAllCustomer() {
        List<Customer> all = customerRepository.findAll();
        log.info("GET: Get all customers");
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Customers", all))
                .build();
    }

    public ApplicationResponse<?> toArchive(Long id, Boolean archive) {
        if(!customerRepository.existsByPassportId(id)){
            return ApplicationResponse
                    .builder()
                    .message("Customer with this passport ID does not exist")
                    .success(false)
                    .build();
        }
        Customer customer = customerRepository.findByPassportId(id);
        customer.setArchived(archive);
        Customer saved = customerRepository.save(customer);
        log.info("UPDATE: Customer with ID:"+id+" archive changed to "+archive);
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Archiving changed customer", saved))
                .build();
    }


    public ApplicationResponse<?> delete(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            return ApplicationResponse.builder()
                    .message("Customer with this ID is not exist")
                    .success(false)
                    .build();
        }
        customerRepository.delete(customer.get());
        log.info("DELETE: Customer with ID:"+id+" is deleted");
        return ApplicationResponse.builder()
                .message("Customer with ID:"+id+" was successfully deleted")
                .success(true)
                .build();
    }

    public ApplicationResponse<?> getDailyCustomers(LocalDateTime startDate, LocalDateTime endDate) {
        Long allByDateWithin = customerRepository.findAllByDateWithin(startDate, endDate);
        log.info("GET: Statistics daily customer: "+startDate.toString());
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Registered customer", allByDateWithin))
                .build();
    }

    public ApplicationResponse<?> getBestEmployee() {
        List<Employee> bestEmployees = customerRepository.findBestEmployee();
        if(bestEmployees.isEmpty()){
            return ApplicationResponse.builder()
                    .message("There is not any employee")
                    .success(false)
                    .build();
        }

        Employee bestEmployee = null;

        for (Employee employee: bestEmployees) {
            if(employee.getDepartment().getName().equals("Mijozlar bilan ishlash bo’limi")){
                bestEmployee = employee;
                break;
            }
        }
        log.info("GET: Statistics best employee: "+bestEmployee.getPassportId());
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Best employee", bestEmployee))
                .build();
    }

    public ApplicationResponse<?> getBestThree() {
        List<Employee> bestEmployees = customerRepository.findBestEmployee();
        if(bestEmployees.isEmpty()){
            return ApplicationResponse.builder()
                    .message("There is not any employee")
                    .success(false)
                    .build();
        }

        List<Employee> bestThreeEmployee = new ArrayList<>();

        for (Employee employee: bestEmployees) {
            if(employee.getDepartment().getName().equals("Mijozlar bilan ishlash bo’limi")){
                if(bestThreeEmployee.size() < 3){
                    bestThreeEmployee.add(employee);
                }else{
                    break;
                }
            }
        }
        log.info("GET: Statistics best 3 employee");
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Best three employee", bestThreeEmployee))
                .build();
    }

    public ApplicationResponse<?> getLastMonth() {

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(30);

        Long count = customerRepository.countCustomersRegisteredLast30Days(startDate, endDate);

        log.info("GET: Statistics last month: "+count);
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Customers registered last month", count))
                .build();
    }

    public ApplicationResponse<?> getTopDay() {

        List<Object[]> dayWithMostRegistrations = customerRepository.findDayWithMostRegistrations(1);
        if (dayWithMostRegistrations.isEmpty()) {
            return ApplicationResponse.builder()
                    .message("No registrations found.")
                    .success(false)
                    .build();
        }

        Object[] mostRegisteredDay = dayWithMostRegistrations.get(0);
        LocalDateTime registrationDay = ((Timestamp) mostRegisteredDay[0]).toLocalDateTime().truncatedTo(ChronoUnit.DAYS);
        long registrationCount = (long) mostRegisteredDay[1];
        log.info("GET: Statistics top day: "+registrationDay.toString());
        return ApplicationResponse.builder()
                .message("OK")
                .success(true)
                .data(Map.of("Most Registered Day", registrationDay, "Registration Count", registrationCount))
                .build();
    }
}
