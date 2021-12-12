package com.example.quiz_company.enumer;

public enum ScheduleType {
    BEFORE(-1, "Начать работать раньше"),
    AFTER(1, "Начать работать позже"),
    STANDART(0, "Остаться в старых рабочих условиях"),
    HOME(10, "Работать из дому");

    private int index;
    private String description;

    ScheduleType(int index, String description) {

        this.index = index;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public static ScheduleType getType(int id) {
         for (ScheduleType s : ScheduleType.values() ) {
            if (s.getIndex() == id) {
                return s;
            }
         }
        return ScheduleType.HOME;
    }

    public String getDescription() {
        return description;
    }

}
