package uz.devior.mohirdev_jpa_vazifa.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    public ApplicationResponse<?> addCustomer(CustomerDTO dto) {
        if(customerRepository.existsByPassportId(dto.getPassportId())){
            return ApplicationResponse
                    .builder()
                    .message("Customer with this passport ID already exists")
                    .success(false)
                    .build();
        }
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPassportSerial(dto.getPassportSerial());
        customer.setPassportNumber(dto.getPassportNumber());
        customer.setPassportId(dto.getPassportId());
        customer.setAddress(dto.getAddress());
        Customer saved = customerRepository.save(customer);
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
        Customer saved = customerRepository.save(customer);
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
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Customer", byPassportId))
                .build();
    }

    public ApplicationResponse<?> getAllCustomer() {
        List<Customer> all = customerRepository.findAll();
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Customers", all))
                .build();
    }
}
