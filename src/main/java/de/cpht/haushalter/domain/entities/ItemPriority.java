package de.cpht.haushalter.domain.entities;

public enum ItemPriority {
    RED_ALERT("RA"),
    PERFECT_TIME("PT"),
    CAN_BE_DONE("CB"),
    NOT_AVAILABLE("NA");

    private String code;

    private ItemPriority(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
