package com.example.quiz_company.valObject;

public class Title {
    private int titleId;
    private String titleName;
    private boolean freeSchedule;

    public Title() {
    }

    public Title(int titleId, String titleName, Boolean freeSchedule) {
        this.titleId = titleId;
        this.titleName = titleName;
        this.freeSchedule = freeSchedule;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public boolean isFreeSchedule() {
        return freeSchedule;
    }

    public void setFreeSchedule(boolean freeSchedule) {
        this.freeSchedule = freeSchedule;
    }
}
