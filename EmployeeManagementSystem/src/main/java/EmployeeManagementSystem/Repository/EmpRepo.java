package EmployeeManagementSystem.Repository;

import EmployeeManagementSystem.Entity.Employee;
import EmployeeManagementSystem.Enum.empStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepo extends JpaRepository<Employee, Long> {

    List<Employee> findByEmpNameContainingIgnoreCase(String empName);

    List<Employee> findByDesignationIgnoreCase(String designation);

    List<Employee> findByStatus(empStatus status);
}