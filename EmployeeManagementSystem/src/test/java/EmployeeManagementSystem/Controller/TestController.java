package EmployeeManagementSystem.Controller;

import EmployeeManagementSystem.Entity.Employee;
import EmployeeManagementSystem.Enum.empStatus;
import EmployeeManagementSystem.Service.employeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class TestController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    employeeService employeeService;

    @Autowired
    ObjectMapper objectMapper;

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
    void testSaveEmployee() throws Exception {
        List<Employee> list = Arrays.asList(employee);

        mockMvc.perform(post("/employee/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Employees Saved Successfully"));
    }

    @Test
    void testViewAll() throws Exception {
        when(employeeService.viewAllEmployee()).thenReturn(Arrays.asList(employee));

        mockMvc.perform(get("/employee/view"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empName")
                        .value("Rahul"));
    }
}