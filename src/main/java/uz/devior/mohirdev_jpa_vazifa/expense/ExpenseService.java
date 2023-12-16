package uz.devior.mohirdev_jpa_vazifa.expense;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.devior.mohirdev_jpa_vazifa.employee.EmployeeRepository;
import uz.devior.mohirdev_jpa_vazifa.shared.ApplicationResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final EmployeeRepository employeeRepository;

    public ApplicationResponse<?> add(ExpenseDTO dto) {
        if (!employeeRepository.existsById(dto.getId())) {
            return ApplicationResponse
                    .builder()
                    .message("Employee with this ID does not exist")
                    .success(false)
                    .build();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Expense expense = Expense
                .builder()
                .type(dto.getType())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .startDate(LocalDateTime.parse(dto.getStartDate(), formatter))
                .employee(employeeRepository.findById(dto.getId()).get())
                .build();
        Expense saved = expenseRepository.save(expense);
        log.info("POST: New expense added: "+saved.getId());
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Saved expense", saved))
                .build();
    }

    public ApplicationResponse<?> update(ExpenseDTO dto, Long expenseId) {
        if (!employeeRepository.existsById(dto.getId())) {
            return ApplicationResponse
                    .builder()
                    .message("Employee with this ID does not exist")
                    .success(false)
                    .build();
        }

        if (!expenseRepository.existsById(expenseId)) {
            return ApplicationResponse
                    .builder()
                    .message("Expense with this ID does not exist")
                    .success(false)
                    .build();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Expense expense = expenseRepository.findById(expenseId).get();
        expense.setPrice(dto.getPrice());
        expense.setDuration(dto.getDuration());
        expense.setStartDate(LocalDateTime.parse(dto.getStartDate(), formatter));
        Expense saved = expenseRepository.save(expense);
        log.info("UPDATE: Expense updated: "+saved.getId());
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Updated expense", saved))
                .build();
    }

    public ApplicationResponse<?> get(Long expenseId) {
        if (!expenseRepository.existsById(expenseId)) {
            return ApplicationResponse
                    .builder()
                    .message("Expense with this ID does not exist")
                    .success(false)
                    .build();
        }
        Expense expense = expenseRepository.findById(expenseId).get();
        log.info("GET: expense by ID:"+expenseId);
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Expense", expense))
                .build();
    }

    public ApplicationResponse<?> getAll(Long employeeId, Pageable pageable) {
        if (!employeeRepository.existsById(employeeId)) {
            return ApplicationResponse
                    .builder()
                    .message("Employee with this ID does not exist")
                    .success(false)
                    .build();
        }

        List<Expense> allByEmployeeId = expenseRepository.findAllByEmployeeId(employeeId, pageable);
        log.info("GET: all expense by pagination");
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Expenses with pagination", allByEmployeeId))
                .build();
    }

    public ApplicationResponse<?> delete(Long id) {

        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isEmpty()) {
            return ApplicationResponse.builder()
                    .message("Expense with this ID is not exist")
                    .success(false)
                    .build();
        }
        expenseRepository.delete(expense.get());
        log.info("DELETE: Expense deleted ID:"+id);
        return ApplicationResponse.builder()
                .message("Expense with ID:" + id + " was successfully deleted")
                .success(true)
                .build();
    }

    public ApplicationResponse<?> getMostExpenseAd() {
        List<Object[]> result = expenseRepository.findMostExpensiveExpenseType();

        if (result.isEmpty()) {
            return ApplicationResponse.builder()
                    .message("Expenses are not exist")
                    .success(false)
                    .build();
        }

        Object[] mostExpensiveType = result.get(0);
        String expenseType = (String) mostExpensiveType[0];
        Double totalCost = (Double) mostExpensiveType[1];
        log.info("GET: most expensive by type:"+expenseType);
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of(expenseType, totalCost))
                .build();
    }

    public ApplicationResponse<?> getMostExpensedEmployee() {
        List<Object[]> result = expenseRepository.findEmployeeWithMostExpenses();

        if (result.isEmpty()) {
            return ApplicationResponse.builder()
                    .message("Expenses are not exist")
                    .success(false)
                    .build();
        }

        Object[] employeeInfo = result.get(0);
        Long employeeId = (Long) employeeInfo[0];
        String employeeName = (String) employeeInfo[1];
        Double  totalExpenses = (Double) employeeInfo[2];
        log.info("GET: employee with most expense: "+employeeId);
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Employee name",employeeName, "Employee ID", employeeId, "Total expenses:",totalExpenses))
                .build();
    }

    public ApplicationResponse<?> getLastMonthStarted() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        long result = expenseRepository.countExpensesStartedLast30Days(startDate);
        log.info("GET: last month satarted expenses: "+result);
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Expenses started in last 30 days", result))
                .build();
    }

    public ApplicationResponse<?> getLastMonthEnded() {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime startDateLast30Days = currentDate.minusDays(30);

        long result = expenseRepository.countExpensesEndedLast30Days(startDateLast30Days, currentDate);
        log.info("GET: last month ended expenses: "+result);
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Expenses ended in last 30 days", result))
                .build();
    }

    public ApplicationResponse<?> getByType() {

        List<Object[]> result = expenseRepository.countExpensesByType();

        if (result.isEmpty()) {
            return ApplicationResponse.builder()
                    .message("Expenses are not exist")
                    .success(false)
                    .build();
        }
        log.info("GET: expenses by type");
        return ApplicationResponse
                .builder()
                .message("Ok")
                .success(true)
                .data(Map.of("Expenses by type", result))
                .build();

    }
}
