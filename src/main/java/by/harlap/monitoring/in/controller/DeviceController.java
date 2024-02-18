package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.dto.device.DeviceResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.DeviceFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.IOUtil;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller class for adding a new device.
 */
public class DeviceController extends BaseController {

    private final DeviceFacade deviceFacade;

    /**
     * Constructs a DeviceController object with the specified DeviceFacade.
     *
     * @param deviceFacade the DeviceFacade object to use for device-related operations
     */
    public DeviceController(DeviceFacade deviceFacade) {
        this.deviceFacade = deviceFacade;
    }

    /**
     * Handles HTTP POST requests for adding a new device.
     * Requires the user to have admin role.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final User activeUser = SecurityUtil.findActiveUser(request);

        SecurityUtil.validateRequiredRole(activeUser, Role.ADMIN);

        final CreateDeviceDto dto = IOUtil.read(request, CreateDeviceDto.class);

        final DeviceResponseDto responseDto = deviceFacade.createDevice(dto);

        IOUtil.write(response, responseDto, HttpServletResponse.SC_CREATED);
    }
}
