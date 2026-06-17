package EmployeeManagementSystem.Entity;

import EmployeeManagementSystem.Enum.empStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "EmployeeManagement")
public class Employee {
    @Id
    @Column(name = "EmployeeID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long empId;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "Name")
    private String empName;


    @Column(name = "Email")
    @NotBlank(message = "Email cannot be blank and Must be in Format")
    @Email
    private String email;

    @NotBlank(message = "Employee Code cannot be blank")
    @Column(name = "EmployeeCode")
    private String empCode;

    @NotBlank(message = "Designation cannot be blank")
    @Column(name = "Designation")
    private String designation;

    @NotNull(message = "Salary cannot be null")
    @Column(name = "Salary")
    private Long salary;

    @Column(name = "Status")
    @NotNull(message = "Status cannot be blank")
    @Enumerated(EnumType.STRING)
    private empStatus status;

    public Employee(Long empId, String empName, String email, String empCode, String designation, Long salary, empStatus status) {
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.empCode = empCode;
        this.designation = designation;
        this.salary = salary;
        this.status = status;
    }

    public Employee() {

    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public empStatus getStatus() {
        return status;
    }

    public void setStatus(empStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", email='" + email + '\'' +
                ", empCode='" + empCode + '\'' +
                ", designation='" + designation + '\'' +
                ", salary=" + salary +
                ", status=" + status +
                '}';
    }
}

