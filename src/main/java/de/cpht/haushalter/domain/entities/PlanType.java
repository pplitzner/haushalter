package de.cpht.haushalter.domain.entities;

public enum PlanType {
    DEFAULT("DL"), CHECKLIST("CL"), TIMEDLIST("TL");

    private String code;

    private PlanType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
