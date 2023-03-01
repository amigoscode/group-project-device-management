package com.amigoscode.devicemanagement.api.verifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthVerifyDevice {

    String deviceIdParamName() default "deviceId";
}
