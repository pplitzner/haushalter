package de.cpht.haushalter.exception;

public class PlanNotDefaultException  extends RuntimeException {
    public PlanNotDefaultException(Long id) {
        super(String.format("Plan with ID %s is not a default plan", id));
    }
}
