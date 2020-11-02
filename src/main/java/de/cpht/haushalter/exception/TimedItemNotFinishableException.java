package de.cpht.haushalter.exception;

public class TimedItemNotFinishableException extends RuntimeException {
    public TimedItemNotFinishableException(Long id) {
        super(String.format("Item %s cannot be finished at the moment", id));
    }
}
