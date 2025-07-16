package uz.divergenny.finservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.divergenny.finservice.entity.Transaction;
import uz.divergenny.finservice.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> create(@RequestParam Long employeeId, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(transactionService.create(employeeId, amount));
    }

    @GetMapping("/user/{employeeId}")
    public ResponseEntity<List<Transaction>> getByUser(@PathVariable Long employeeId) {
        return ResponseEntity.ok(transactionService.getByUser(employeeId));
    }

    @GetMapping("/date")
    public ResponseEntity<List<Transaction>> getByDateRange(
            @RequestParam String from,
            @RequestParam String to) {
        return ResponseEntity.ok(transactionService.getByDateRange(
                LocalDateTime.parse(from), LocalDateTime.parse(to)));
    }
}
