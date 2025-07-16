package uz.divergenny.finservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.divergenny.finservice.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByEmployeeId(Long employeeId);

    List<Transaction> findByTimestampBetween(LocalDateTime from, LocalDateTime to);
}
