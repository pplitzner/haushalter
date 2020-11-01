package de.cpht.haushalter.domain.entities;

public enum ItemType {
    DEFAULT("DI"), TIMED("TI");

    private String code;

    private ItemType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
