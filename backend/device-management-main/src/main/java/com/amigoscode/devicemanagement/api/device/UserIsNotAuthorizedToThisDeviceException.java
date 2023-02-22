package com.amigoscode.devicemanagement.api.device;

public class UserIsNotAuthorizedToThisDeviceException extends RuntimeException{

    public UserIsNotAuthorizedToThisDeviceException(String message) {
        super(message);
    }

}