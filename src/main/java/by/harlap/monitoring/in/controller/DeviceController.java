package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.dto.device.DeviceResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.DeviceFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller class for adding a new device.
 */
@Controller
@RequiredArgsConstructor
public class DeviceController{

    private final DeviceFacade deviceFacade;
    private final SecurityUtil securityUtil;

    @PostMapping(value = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceResponseDto> doPost(@RequestAttribute("username") String username, @RequestBody CreateDeviceDto deviceDto){
        final User activeUser = securityUtil.findActiveUser(username);

        securityUtil.validateRequiredRole(activeUser, Role.ADMIN);

        final DeviceResponseDto responseDto = deviceFacade.createDevice(deviceDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
