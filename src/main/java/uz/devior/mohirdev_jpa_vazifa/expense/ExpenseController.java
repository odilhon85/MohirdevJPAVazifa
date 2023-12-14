package uz.devior.mohirdev_jpa_vazifa.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ExpenseDTO dto) {
        ApplicationResponse<?> response = expenseService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestBody ExpenseDTO dto,
            @RequestParam Long expenseId) {
        ApplicationResponse<?> response = expenseService.update(dto, expenseId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam Long expenseId) {
        ApplicationResponse<?> response = expenseService.get(expenseId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(
            @RequestParam Long employeeId,
            @RequestParam Optional<Integer> pageNumber,
            @RequestParam Optional<Integer> pageSize) {
        Pageable pageable = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(20));
        ApplicationResponse<?> response = expenseService.getAll(employeeId, pageable);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteExpense(@RequestParam Long id) {
        ApplicationResponse<?> response = expenseService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/stat/most-expense")
    public ResponseEntity<?> getMostExpenseAd() {
        ApplicationResponse<?> response = expenseService.getMostExpenseAd();
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/stat/top-employee")
    public ResponseEntity<?> getMostExpensedEmployee() {
        ApplicationResponse<?> response = expenseService.getMostExpensedEmployee();
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/stat/started-last-month")
    public ResponseEntity<?> getLastMonthStarted() {
        ApplicationResponse<?> response = expenseService.getLastMonthStarted();
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/stat/ended-last-month")
    public ResponseEntity<?> getLastMonthEnded() {
        ApplicationResponse<?> response = expenseService.getLastMonthEnded();
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

    @GetMapping("/stat/by-type")
    public ResponseEntity<?> getByType() {
        ApplicationResponse<?> response = expenseService.getByType();
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
