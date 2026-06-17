package EmployeeManagementSystem.Service;

import EmployeeManagementSystem.Entity.Employee;
import EmployeeManagementSystem.Enum.empStatus;
import EmployeeManagementSystem.Repository.EmpRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TestService {

    @Mock
    EmpRepo empRepo;                    // Fake Repository

    @InjectMocks
    employeeService empService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmpId(1L);
        employee.setEmpName("Rahul");
        employee.setEmail("rahul@gmail.com");
        employee.setEmpCode("EMP001");
        employee.setDesignation("Developer");
        employee.setSalary(50000L);
        employee.setStatus(empStatus.ACTIVE);
    }

    @Test
    void testSaveEmployee() {
        List<Employee> list = Arrays.asList(employee);

        empService.saveEmployee(list);

        verify(empRepo, times(1)).saveAll(list);
    }

    @Test
    void testViewAllEmployee() {
        when(empRepo.findAll()).thenReturn(Arrays.asList(employee));

        List<Employee> result = empService.viewAllEmployee();

        assertEquals(1, result.size());
        assertEquals("Rahul", result.get(0).getEmpName());
        assertEquals("rahul@gmail.com", result.get(0).getEmail());
    }
}