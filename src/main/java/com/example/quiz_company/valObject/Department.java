package com.example.quiz_company.valObject;

public class Department {
    private int departmentId;
    private String departmentName;
    private Company company;
    private boolean freeSchedule;
    private boolean syncSchedule;
    private int scheduleOffset;


    public Department() {
    }

    public Department(int departmentId, String departmentName, Company company, boolean freeSchedule, boolean syncSchedule, int scheduleOffset) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.company = company;
        this.freeSchedule = freeSchedule;
        this.syncSchedule = syncSchedule;
        this.scheduleOffset = scheduleOffset;
    }


    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isFreeSchedule() {
        return freeSchedule;
    }

    public void setFreeSchedule(boolean freeSchedule) {
        this.freeSchedule = freeSchedule;
    }

    public boolean isSyncSchedule() {
        return syncSchedule;
    }

    public void setSyncSchedule(boolean syncSchedule) {
        this.syncSchedule = syncSchedule;
    }

    public int getScheduleOffset() {
        return scheduleOffset;
    }

    public void setScheduleOffset(int scheduleOffset) {
        this.scheduleOffset = scheduleOffset;
    }
}
