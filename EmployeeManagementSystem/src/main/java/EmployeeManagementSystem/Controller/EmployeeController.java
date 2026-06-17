package EmployeeManagementSystem.Controller;

import EmployeeManagementSystem.Entity.Employee;
import EmployeeManagementSystem.Enum.empStatus;
import EmployeeManagementSystem.Service.employeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    employeeService employeeService;

    @GetMapping("/test")
    public String testFirst() {
        return "Employee Management System Started Successfully!";
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveData(
            @Valid @RequestBody List<Employee> employees) {
        logger.info("POST /employee/save called with {} record(s)", employees.size());
        employeeService.saveEmployee(employees);
        return new ResponseEntity<>("Employees Saved Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Employee>> viewAll() {
        logger.info("GET /employee/view called");
        return ResponseEntity.ok(employeeService.viewAllEmployee());
    }

    @GetMapping("/view/{employeeId}")
    public ResponseEntity<Optional<Employee>> getById(@PathVariable Long employeeId) {
        logger.info("GET /employee/view/{} called", employeeId);
        return ResponseEntity.ok(employeeService.viewById(employeeId));
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long employeeId,
            @Valid @RequestBody Employee employee) {
        logger.info("PUT /employee/update/{} called", employeeId);
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employee));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId) {
        logger.info("DELETE /employee/delete/{} called", employeeId);
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(
                "Employee with ID " + employeeId + " deleted successfully!");

    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Employee>> searchByName(@RequestParam String name) {
        logger.info("GET /employee/search/name?name={} called", name);
        return ResponseEntity.ok(employeeService.searchByName(name));
    }

    @GetMapping("/search/designation")
    public ResponseEntity<List<Employee>> searchByDesignation(
            @RequestParam String designation) {
        logger.info("GET /employee/search/designation?designation={}", designation);
        return ResponseEntity.ok(employeeService.searchByDesignation(designation));
    }

    @GetMapping("/search/status")
    public ResponseEntity<List<Employee>> filterByStatus(@RequestParam empStatus status) {
        logger.info("GET /employee/search/status?status={} called", status);
        return ResponseEntity.ok(employeeService.filterByStatus(status));
    }
}