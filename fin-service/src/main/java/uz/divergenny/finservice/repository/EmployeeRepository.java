package uz.divergenny.finservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.divergenny.finservice.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
