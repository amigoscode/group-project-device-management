package com.amigoscode.devicemanagement.api.handler;

import com.amigoscode.devicemanagement.api.response.ErrorResponse;
import com.amigoscode.devicemanagement.api.verifier.UserIsNotAuthorizedToThisDeviceException;
import com.amigoscode.devicemanagement.domain.device.DeviceNotFoundException;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingNotFoundException;
import com.amigoscode.devicemanagement.domain.user.exception.UserNotFoundException;
import com.amigoscode.devicemanagement.external.storage.device.DeviceAlreadyExistsException;
import com.amigoscode.devicemanagement.external.storage.devicesetting.DeviceSettingAlreadyExistsException;
import com.amigoscode.devicemanagement.external.storage.user.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(DeviceNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleDeviceNotFoundException(DeviceNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeviceAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleDeviceAlreadyExistsException(DeviceAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DeviceSettingNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleDeviceSettingNotFoundException(DeviceSettingNotFoundException ex){
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeviceSettingAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleDeviceSettingAlreadyExistsException(DeviceSettingAlreadyExistsException ex){
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserIsNotAuthorizedToThisDeviceException.class)
    public final ResponseEntity<ErrorResponse> handleUserIsNotAuthorizedToThisDeviceException(UserIsNotAuthorizedToThisDeviceException ex) {
        return buildResponse(ex, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ErrorResponse> handleCommandNotSupportedException(IOException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));
    }

    private <E extends RuntimeException> ResponseEntity<ErrorResponse> buildResponse(E exception, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(exception.getMessage()));
    }

}