package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.annotations.Loggable;
import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.mapper.DeviceMapper;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controller class for adding a new device.
 */
@Loggable
@WebServlet(urlPatterns = "/devices")
public class AddNewDeviceController extends AbstractController {

    private DeviceService deviceService;
    private DeviceMapper deviceMapper;

    /**
     * Initializes the controller by initializing necessary dependencies.
     */
    @Override
    public void init() {
        super.init();

        deviceService = DependencyFactory.findService(DeviceService.class);
        deviceMapper = DependencyFactory.findMapper(DeviceMapper.class);
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
        final User activeUser = findActiveUser(request);

        validateRequiredRole(activeUser, Role.ADMIN);

        CreateDeviceDto dto = read(request, CreateDeviceDto.class);

        Device device = deviceMapper.toEntity(dto);

        deviceService.save(device);

        write(response, device, HttpServletResponse.SC_CREATED);
    }
}
