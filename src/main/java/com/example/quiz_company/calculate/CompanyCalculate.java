package com.example.quiz_company.calculate;

import com.example.quiz_company.dao.DepartmentDAO;
import com.example.quiz_company.valObject.Company;

import java.util.List;

public class CompanyCalculate implements Calculate{
    private Company company;
    private List<DepartmentCalculate> departments;
    private double efficiency;

    public CompanyCalculate() {
    }

    public CompanyCalculate(Company company) {
        this.company = company;
        this.departments = new DepartmentDAO().findCompanyDepartments(company);
        this.efficiency = 0;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<DepartmentCalculate> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentCalculate> departments) {
        this.departments = departments;
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
        departments.forEach(d -> d.calculateEfficienty());
        double sum_efficiency = departments.stream().mapToDouble(d -> d.getEfficiency()).sum();
        double count_dep = departments.size();
        if (count_dep > 0) {
            this.efficiency = sum_efficiency / count_dep;
        }
    }
}
