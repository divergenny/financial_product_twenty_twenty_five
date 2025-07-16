package uz.divergenny.finservice.service;

import org.springframework.stereotype.Service;
import uz.divergenny.finservice.dto.EmployeeRequestDto;
import uz.divergenny.finservice.dto.EmployeeResponseDto;
import uz.divergenny.finservice.entity.Employee;
import uz.divergenny.finservice.repository.EmployeeRepository;

import java.math.BigDecimal;

@Service
public class EmployeeService {
    private final EmployeeRepository userRepo;

    public EmployeeService(EmployeeRepository userRepo) {
        this.userRepo = userRepo;
    }

    public EmployeeResponseDto create(EmployeeRequestDto employeeRequestDto) {
        Employee employee = new Employee();
        employee.setName(employeeRequestDto.getName());
        employee.setBalance(BigDecimal.ZERO);
        userRepo.save(employee);
        return mapToDto(employee);
    }

    public EmployeeResponseDto update(Long id, EmployeeRequestDto employeeRequestDto) {
        Employee employee = userRepo.findById(id).orElseThrow();
        employee.setName(employeeRequestDto.getName());
        userRepo.save(employee);
        return mapToDto(employee);
    }

    public EmployeeResponseDto getById(Long id) {
        Employee employee = userRepo.findById(id).orElseThrow();
        return mapToDto(employee);
    }

    private EmployeeResponseDto mapToDto(Employee employee) {
        return new EmployeeResponseDto(employee.getId(), employee.getName(), employee.getBalance());
    }
}
