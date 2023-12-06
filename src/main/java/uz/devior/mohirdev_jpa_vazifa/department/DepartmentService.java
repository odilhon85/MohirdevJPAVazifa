package uz.devior.mohirdev_jpa_vazifa.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public ApplicationResponse<?> add(DepartmentDTO dto) {
        if(departmentRepository.existsByName(dto.getName())){
            return ApplicationResponse.builder()
                    .message("Department with this name is exists")
                    .success(false)
                    .build();
        }
        Department department = new Department();
        department.setName(dto.getName());
        Department saved = departmentRepository.save(department);

        return ApplicationResponse.builder()
                .message("Ok")
                .success(true)
                .data(Map.of("New department", saved))
                .build();
    }


    public ApplicationResponse<?> update(Long id, DepartmentDTO dto) {
        if(!departmentRepository.existsById(id)){
            return ApplicationResponse.builder()
                    .message("Department with this name is not exists")
                    .success(false)
                    .build();
        }
        Optional<Department> byId = departmentRepository.findById(id);
        Department department = byId.get();
        department.setName(dto.getName());
        Department saved = departmentRepository.save(department);
        return ApplicationResponse.builder()
                .message("Ok")
                .success(true)
                .data(Map.of("New department", saved))
                .build();

    }

    public ApplicationResponse<?> get() {
        List<Department> departments = departmentRepository.findAll();
        return ApplicationResponse.builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Departments", departments))
                .build();
    }

    public ApplicationResponse<?> delete(Long id) {
        if(!departmentRepository.existsById(id)){
            return ApplicationResponse.builder()
                    .message("Department with this name is not exists")
                    .success(false)
                    .build();
        }
        Optional<Department> byId = departmentRepository.findById(id);
        departmentRepository.delete(byId.get());
        return ApplicationResponse.builder()
                .message("Department with id="+id+" deleted")
                .success(true)
                .build();
    }
}
