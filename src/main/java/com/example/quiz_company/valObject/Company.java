package com.example.quiz_company.valObject;

public class Company {
    private int companyId;
    private String name;

    public Company() {
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company(int companyId, String name) {
        this.companyId = companyId;
        this.name = name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }
}
