package uz.devior.mohirdev_jpa_vazifa.expense;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByEmployeeId(Long employeeId, Pageable pageable);

    @Query("SELECT e.type, SUM(e.price) as totalCost " +
            "FROM Expense e " +
            "GROUP BY e.type " +
            "ORDER BY totalCost DESC")
    List<Object[]> findMostExpensiveExpenseType();

    @Query("SELECT e.employee.id, e.employee.firstName, SUM(e.price) as totalCost " +
            "FROM Expense e " +
            "GROUP BY e.employee.id, e.employee.firstName " +
            "ORDER BY totalCost DESC")
    List<Object[]> findEmployeeWithMostExpenses();

    @Query("SELECT COUNT(e.id) " +
            "FROM Expense e " +
            "WHERE e.startDate >= :startDate")
    long countExpensesStartedLast30Days(LocalDateTime startDate);

    @Query(value = "SELECT COUNT(e.id) " +
            "FROM expense e " +
            "WHERE (e.start_date + e.duration * INTERVAL '1' DAY) < :currentDate " +
            "AND e.start_date >= :startDate", nativeQuery = true)
    long countExpensesEndedLast30Days(LocalDateTime startDate, LocalDateTime currentDate);

    @Query("SELECT e.type, COUNT(e.id) " +
            "FROM Expense e " +
            "GROUP BY e.type")
    List<Object[]> countExpensesByType();
}
