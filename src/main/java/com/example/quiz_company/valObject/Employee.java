package com.example.quiz_company.valObject;

public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private Department department;
    private Title title;
    private EmployeeSchedule employeeSchedule;
    private EmployeeSalary employeeSalary;

    public Employee() {
    }

    public Employee(int employeeId, String firstName, String lastName, Department department, Title title, EmployeeSchedule employeeSchedule, EmployeeSalary employeeSalary) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.title = title;
        this.employeeSchedule = employeeSchedule;
        this.employeeSalary = employeeSalary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public EmployeeSchedule getEmployeeSchedule() {
        return employeeSchedule;
    }

    public void setEmployeeSchedule(EmployeeSchedule employeeSchedule) {
        this.employeeSchedule = employeeSchedule;
    }

    public EmployeeSalary getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(EmployeeSalary employeeSalary) {
        this.employeeSalary = employeeSalary;
    }
}
