package com.amigoscode.devicemanagement.api.device;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface AuthVerifyDevice {

    String deviceIdParamName() default "deviceId";
}
