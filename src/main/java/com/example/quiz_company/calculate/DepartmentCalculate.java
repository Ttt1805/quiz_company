package com.example.quiz_company.calculate;

import com.example.quiz_company.dao.EmployeeDAO;
import com.example.quiz_company.valObject.Department;
import com.example.quiz_company.valObject.Employee;

import java.util.List;

public class DepartmentCalculate implements Calculate{
    private Department department;
    private List<EmployeeCalculate> employees;
    private double efficiency;

    public DepartmentCalculate() {
    }

    public DepartmentCalculate(Department department, List<EmployeeCalculate> employees) {
        this.department = department;
        this.employees = employees;
        this.efficiency = 0;
    }

    public DepartmentCalculate(Department department) {
        this.department = department;
        this.employees = new EmployeeDAO().getDepEmployees(department);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<EmployeeCalculate> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeCalculate> employees) {
        this.employees = employees;
    }


    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    @Override
    public void calculateEfficienty() {
        this.efficiency = 0;
        employees.forEach(e -> e.calculateEfficienty());
        double count_empl = employees.size();
        if (count_empl > 0 ) {
            double sum_efficiency = employees.stream().mapToDouble(e -> e.getEfficiency()).sum();
            this.efficiency = sum_efficiency / count_empl;
        }
    }
}
