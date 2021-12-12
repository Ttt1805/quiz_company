package com.example.quiz_company.valObject;

import com.example.quiz_company.enumer.ScheduleType;

public class EmployeeSchedule {
    private ScheduleType typeSchedule;
    private int scheduleOffset;

    public EmployeeSchedule() {
        this.typeSchedule = ScheduleType.STANDART;
        this.scheduleOffset = 0;
    }

    public EmployeeSchedule(ScheduleType typeSchedule) {
        this(typeSchedule, 0);
    }

    public EmployeeSchedule(ScheduleType typeSchedule, int scheduleOffset) {
        this.typeSchedule = typeSchedule;
        this.scheduleOffset = scheduleOffset;
    }

    public ScheduleType getTypeSchedule() {
        return typeSchedule;
    }

    public void setTypeSchedule(ScheduleType typeSchedule) {
        this.typeSchedule = typeSchedule;
    }

    public int getScheduleOffset() {
        return scheduleOffset;
    }

    public void setScheduleOffset(int scheduleOffset) {
        this.scheduleOffset = scheduleOffset;
    }
}
