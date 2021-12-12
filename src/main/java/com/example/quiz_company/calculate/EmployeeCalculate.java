package com.example.quiz_company.calculate;

import com.example.quiz_company.enumer.ScheduleType;
import com.example.quiz_company.valObject.*;

public class EmployeeCalculate implements Calculate{
    private Employee employee;
    private double efficiency;

    public EmployeeCalculate(Employee employee) {
        this.employee = employee;
        this.efficiency = 0;
    }

    public EmployeeCalculate() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        Department department =employee.getDepartment();
        Title title = employee.getTitle();
        EmployeeSchedule employeeSchedule = employee.getEmployeeSchedule();
        if (department.isSyncSchedule()) {
            // Синхронный график
            if (employeeSchedule.getTypeSchedule() == ScheduleType.HOME) {
                this.efficiency = 0;
            } else {
                this.efficiency = Constant.PERCENT_EFFICIENCY*(department.getScheduleOffset() - employeeSchedule.getScheduleOffset())/8;
            }

        } else if (!department.isFreeSchedule()){
            if (employeeSchedule.getTypeSchedule() == ScheduleType.HOME) {
                this.efficiency = 0;
            } else {
                this.efficiency = -Constant.PERCENT_EFFICIENCY * Math.abs(employeeSchedule.getScheduleOffset())/8;
            }
        } else {
            // разрешен свободный график подразделения
            if (!title.isFreeSchedule()) {
                if (employeeSchedule.getTypeSchedule() == ScheduleType.HOME) {
                    this.efficiency = 0;
                } else {
                    this.efficiency = -Constant.PERCENT_EFFICIENCY * Math.abs(employeeSchedule.getScheduleOffset())/8;
                }
            } else {
                if (employeeSchedule.getTypeSchedule() == ScheduleType.HOME) {
                    this.efficiency = Constant.PERCENT_EFFICIENCY / 2;
                } else {
                    this.efficiency = Constant.PERCENT_EFFICIENCY * Math.abs(employeeSchedule.getScheduleOffset())/8;
                }
           }
        }
    }
}
