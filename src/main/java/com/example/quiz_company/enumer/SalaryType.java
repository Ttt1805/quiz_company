package com.example.quiz_company.enumer;

public enum SalaryType {
    FIX_SALARY(0, "Оклад"),
    HOURLY_WAGES(1, "Почасовая зарплата"),
    SALARY_AND_PERCENT(2, "Оклад + проценты");

    private int index;
    private String description;

    SalaryType(int index, String description) {
        this.index = index;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public static SalaryType getType(int id) {
        for (SalaryType s: SalaryType.values()) {
            if (s.getIndex() == id) {
                return s;
            }
        }
        return SalaryType.FIX_SALARY;
    }

    public String getDescription() {
        return description;
    }
}
