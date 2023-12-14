package uz.devior.mohirdev_jpa_vazifa.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.devior.mohirdev_jpa_vazifa.customer.Customer;
import uz.devior.mohirdev_jpa_vazifa.department.Department;
import uz.devior.mohirdev_jpa_vazifa.expense.Expense;
import uz.devior.mohirdev_jpa_vazifa.shared.Role;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String passportSerial;

    @Column(nullable = false)
    private Long passportNumber;

    @Column(nullable = false, unique = true)
    private Long passportId;

    @JsonIgnore
    private Long username;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private Integer salary;

    @Column(nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Customer> customerList;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Expense> expenseList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername(){
        return String.valueOf(this.username);
    }
}
