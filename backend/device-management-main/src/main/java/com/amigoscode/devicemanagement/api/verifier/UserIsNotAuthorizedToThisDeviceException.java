package com.amigoscode.devicemanagement.api.verifier;

public class UserIsNotAuthorizedToThisDeviceException extends RuntimeException{

    public UserIsNotAuthorizedToThisDeviceException(String message) {
        super(message);
    }

}