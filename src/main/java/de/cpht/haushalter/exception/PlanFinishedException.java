package de.cpht.haushalter.exception;

public class PlanFinishedException extends RuntimeException {
    public PlanFinishedException(Long id) {
            super("Plan already finished!");
        }
}
