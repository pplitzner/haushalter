package de.cpht.haushalter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(PlanFinishedException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    String planFinishedHandler(PlanFinishedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PlanItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String itemNotFoundHandler(PlanItemNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TimedItemNotFinishableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String timedItemNotFinishableHandler(TimedItemNotFinishableException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PlanNotDefaultException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    String planNotDefaultHandler(PlanNotDefaultException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PlanNotFoundException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    String planNotFoundHandler(PlanNotFoundException ex) {
        return ex.getMessage();
    }
}
