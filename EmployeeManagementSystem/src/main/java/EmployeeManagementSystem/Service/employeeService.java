package EmployeeManagementSystem.Service;

import EmployeeManagementSystem.EncryptionDecryption.AESEncryption;
import EmployeeManagementSystem.Entity.Employee;
import EmployeeManagementSystem.Enum.empStatus;
import EmployeeManagementSystem.ExceptionHandler.EmployeeNotFoundException;
import EmployeeManagementSystem.Repository.EmpRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class employeeService {

    private static final Logger logger = LoggerFactory.getLogger(employeeService.class);

    @Autowired
    EmpRepo empRepo;


    public void saveEmployee(List<Employee> employees) {
        logger.trace("saveEmployee() called with {} employee(s): {}", employees.size(), employees);

        logger.info("Saving {} employee(s)", employees.size());

        employees.forEach(emp -> {
            emp.setEmail(AESEncryption.encrypt(emp.getEmail()));
            logger.info("Data Encrypted Successfully");
        });

        empRepo.saveAll(employees);
        logger.info("Employees saved successfully");

        logger.trace("saveEmployee() completed");
    }

    public List<Employee> viewAllEmployee() {
        logger.trace("viewAllEmployee() called");

        logger.info("Fetching all employees");


        List<Employee> list = empRepo.findAll();

        list.forEach(emp->{
            emp.setEmail(AESEncryption.decrypt(emp.getEmail()));
            logger.info("Data Decryptedd Successfully");
        });

        if (list.isEmpty()) {
            logger.warn("No employees found in the database");
        } else {
            logger.info("Total employees found: {}", list.size());
        }

        logger.trace("viewAllEmployee() returning {} records: {}", list.size(), list);
        return list;
    }


    public Optional<Employee> viewById(Long id) {
        logger.trace("viewById() called with ID: {}", id);

        logger.info("Fetching employee with ID: {}", id);

        Optional<Employee> result = Optional.of(empRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new EmployeeNotFoundException(
                            "Employee not found with ID: " + id);
                }));

        logger.trace("viewById() returning: {}", result);
        return result;
    }


    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        logger.trace("updateEmployee() called with ID: {} and data: {}", id, updatedEmployee);

        logger.info("Updating employee with ID: {}", id);

        Employee existing = empRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", id);
                    return new EmployeeNotFoundException(
                            "Employee not found with ID: " + id);
                });

        logger.trace("Existing data before update: {}", existing);

        existing.setEmpName(updatedEmployee.getEmpName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setEmpCode(updatedEmployee.getEmpCode());
        existing.setDesignation(updatedEmployee.getDesignation());
        existing.setSalary(updatedEmployee.getSalary());
        existing.setStatus(updatedEmployee.getStatus());

        Employee saved = empRepo.save(existing);

        logger.info("Employee with ID {} updated successfully", id);
        logger.trace("updateEmployee() returning updated data: {}", saved);

        return saved;
    }


    public void deleteEmployee(Long id) {
        logger.trace("deleteEmployee() called with ID: {}", id);

        logger.info("Deleting employee with ID: {}", id);

        if (!empRepo.existsById(id)) {
            logger.error("Cannot delete - Employee not found with ID: {}", id);
            throw new EmployeeNotFoundException(
                    "Employee not found with ID: " + id);
        }

        empRepo.deleteById(id);

        logger.info("Employee with ID {} deleted successfully", id);
        logger.trace("deleteEmployee() completed for ID: {}", id);
    }


    public List<Employee> searchByName(String name) {
        logger.trace("searchByName() called with name: '{}'", name);

        if (name == null || name.trim().isEmpty()) {
            logger.warn("searchByName() called with blank or null name");
        }

        logger.info("Searching employees by name: {}", name);
        List<Employee> result = empRepo.findByEmpNameContainingIgnoreCase(name);

        if (result.isEmpty()) {
            logger.warn("No employees found with name containing: '{}'", name);
        } else {
            logger.trace("searchByName() found {} result(s): {}", result.size(), result);
        }

        return result;
    }


    public List<Employee> searchByDesignation(String designation) {
        logger.trace("searchByDesignation() called with: '{}'", designation);

        logger.info("Searching by designation: {}", designation);
        List<Employee> result = empRepo.findByDesignationIgnoreCase(designation);

        if (result.isEmpty()) {
            logger.warn("No employees found with designation: '{}'", designation);
        } else {
            logger.trace("searchByDesignation() found {} result(s): {}", result.size(), result);
        }

        return result;
    }

    public List<Employee> filterByStatus(empStatus status) {
        logger.trace("filterByStatus() called with status: {}", status);

        logger.info("Filtering employees by status: {}", status);
        List<Employee> result = empRepo.findByStatus(status);

        if (result.isEmpty()) {
            logger.warn("No employees found with status: '{}'", status);
        } else {
            logger.trace("filterByStatus() found {} result(s): {}", result.size(), result);
        }

        return result;
    }
}