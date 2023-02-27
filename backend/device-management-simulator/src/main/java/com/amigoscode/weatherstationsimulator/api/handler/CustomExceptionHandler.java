package com.amigoscode.weatherstationsimulator.api.handler;

import com.amigoscode.weatherstationsimulator.api.response.ErrorResponse;
import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MeasurementNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleTemperatureNotFoundException(MeasurementNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    private <E extends RuntimeException> ResponseEntity<ErrorResponse> buildResponse(E exception, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(exception.getMessage()));
    }
}
