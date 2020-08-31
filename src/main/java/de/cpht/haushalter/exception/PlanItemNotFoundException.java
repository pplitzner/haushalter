package de.cpht.haushalter.exception;

public class PlanItemNotFoundException extends RuntimeException {
    public PlanItemNotFoundException(Long id) {
        super("Could not find item " + id);
    }
}
