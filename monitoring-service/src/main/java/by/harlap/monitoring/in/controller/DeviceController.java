package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.dto.device.DeviceResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.DeviceFacade;
import by.harlap.monitoring.in.controller.openapi.DeviceOpenAPI;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for adding a new device.
 */
@RestController
@RequiredArgsConstructor
public class DeviceController implements DeviceOpenAPI {

    private final DeviceFacade deviceFacade;
    private final SecurityUtil securityUtil;

    /**
     * Handles requests to add a new device.
     *
     * @param username  the username obtained from the request attribute
     * @param deviceDto the CreateDeviceDto containing data for the new device
     * @return ResponseEntity containing the response data for the created device
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/devices")
    public DeviceResponseDto saveDevice(@RequestAttribute("username") String username, @RequestBody @Valid CreateDeviceDto deviceDto) {
        final User activeUser = securityUtil.findActiveUser(username);
        securityUtil.validateRequiredRole(activeUser, Role.ADMIN);

        return deviceFacade.createDevice(deviceDto);
    }
}
