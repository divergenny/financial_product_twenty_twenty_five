package uz.divergenny.finservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.divergenny.finservice.entity.Employee;
import uz.divergenny.finservice.entity.Transaction;
import uz.divergenny.finservice.repository.EmployeeRepository;
import uz.divergenny.finservice.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepo;
    private final EmployeeRepository userRepo;

    public TransactionService(TransactionRepository transactionRepository, EmployeeRepository userRepo) {
        this.transactionRepo = transactionRepository;
        this.userRepo = userRepo;
    }

    @Transactional
    public Transaction create(Long employeeId, BigDecimal amount) {
        Employee employee = userRepo.findById(employeeId).orElseThrow();

        employee.setBalance(employee.getBalance().add(amount));
        userRepo.save(employee);

        Transaction transaction = new Transaction();
        transaction.setEmployee(employee);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepo.save(transaction);
    }

    public List<Transaction> getByUser(Long employeeId) {
        return transactionRepo.findByEmployeeId(employeeId);
    }

    public List<Transaction> getByDateRange(LocalDateTime from, LocalDateTime to) {
        return transactionRepo.findByTimestampBetween(from, to);
    }
}
