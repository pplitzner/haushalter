package de.cpht.haushalter.exception;

public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(Long id) {
        super("Could not find plan " + id);
    }
}
