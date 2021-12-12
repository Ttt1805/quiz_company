package com.example.quiz_company.valObject;

import com.example.quiz_company.enumer.SalaryType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;


import java.time.LocalDate;

public class EmployeeSalary {
    private SalaryType salaryType;
    private int salary;
    private int percent;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private  LocalDate salary_day;

    public EmployeeSalary() {
        this.salaryType = SalaryType.FIX_SALARY;
        this.salary = 0;
        this.percent = 0;
    }

    public EmployeeSalary(SalaryType salaryType, int salary, int percent, LocalDate salary_day) {
        this.salaryType = salaryType;
        this.salary = salary;
        this.percent = percent;
        this.salary_day = salary_day;
    }

    public EmployeeSalary(SalaryType salaryType, int salary, LocalDate salary_day) {
        this (salaryType, salary, 0, salary_day);
    }

    public LocalDate getSalary_day() {
        return salary_day;
    }

    public void setSalary_day(LocalDate salary_day) {
        this.salary_day = salary_day;
    }

    public SalaryType getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(SalaryType salaryType) {
        this.salaryType = salaryType;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
