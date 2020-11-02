package de.cpht.haushalter.domain.entities;

public enum ItemPriority {
    NOT_AVAILABLE("NA"), CAN_BE_DONE("CB"), PERFECT_TIME("PT"), RED_ALERT("RA");

    private String code;

    private ItemPriority(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
