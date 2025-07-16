package uz.divergenny.finservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.divergenny.finservice.dto.EmployeeRequestDto;
import uz.divergenny.finservice.dto.EmployeeResponseDto;
import uz.divergenny.finservice.service.EmployeeService;

@RestController
@RequestMapping("/users")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeResponseDto> create(@RequestBody @Valid EmployeeRequestDto employee) {
        return ResponseEntity.ok(employeeService.create(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> update(@PathVariable Long id, @RequestBody @Valid EmployeeRequestDto employee) {
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }
}
