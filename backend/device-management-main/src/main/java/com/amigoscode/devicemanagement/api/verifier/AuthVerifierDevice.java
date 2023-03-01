package com.amigoscode.devicemanagement.api.verifier;

import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.amigoscode.devicemanagement.domain.user.model.UserRole.ADMIN;


@Component
@Aspect
@RequiredArgsConstructor
public class AuthVerifierDevice {

    private final UserService userService;
    private final DeviceService deviceService;


    @Before(value = "@annotation(authVerifyDevice)")
    public void userIsAdminOwnerOfTask(JoinPoint joinPoint, AuthVerifyDevice authVerifyDevice) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String deviceId = (String) getParameterByName(joinPoint, authVerifyDevice.deviceIdParamName());

        User user = userService.findByEmail(((UserPrincipal) authentication.getPrincipal()).getUsername());
        boolean authorizationConfirmed = deviceService.findById(deviceId).isUserTheOwnerOfThisDevice(user.getId()) || user.getRoles().contains(ADMIN);

        if (!authorizationConfirmed) {
            throw new UserIsNotAuthorizedToThisDeviceException("User is not authorized to this device.");
        }
    }

    private Object getParameterByName(JoinPoint joinPoint, String parameterName) {
        MethodSignature methodSig = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        if (args.length==0){
            throw new UserIsNotAuthorizedToThisDeviceException("Parameter " + parameterName + " not provided.");
        }
        String[] parametersName = methodSig.getParameterNames();
        int index = Arrays.asList(parametersName).indexOf(parameterName);
        return args[index];
    }

}
