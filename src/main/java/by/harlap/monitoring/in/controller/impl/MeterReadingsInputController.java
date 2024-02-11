package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingsDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.exception.GenericHttpException;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.Device;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The MeterReadingsInputController class extends AbstractController and is responsible for handling meter readings input requests.
 */
@WebServlet(urlPatterns = "/inputMeterReadings")
public class MeterReadingsInputController extends AbstractController {

    private MeterReadingsService meterReadingsService;
    private DeviceService deviceService;

    /**
     * Initializes the controller by initializing necessary dependencies.
     */
    @Override
    public void init() {
        super.init();

        meterReadingsService = DependencyFactory.findService(MeterReadingsService.class);
        deviceService = DependencyFactory.findService(DeviceService.class);
    }

    /**
     * Handles HTTP POST requests for inputting meter readings.
     *
     * @param requestContext the HTTP servlet request
     * @param response       the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest requestContext, HttpServletResponse response) throws IOException {
        final User activeUser = findActiveUser(requestContext);

        validateRequiredRole(activeUser, Role.USER);
        validateMetricsExistence(activeUser);

        final CreateMeterReadingsDto requestData = read(requestContext, CreateMeterReadingsDto.class);
        final String responseData = createMeterReadingRecord(activeUser, requestData);

        write(response, responseData, HttpServletResponse.SC_CREATED);
    }

    private String createMeterReadingRecord(User user, CreateMeterReadingsDto data) {
        final Map<String, Double> valuesByName = data.getDeviceValues();
        Map<Device, Double> valuesByDevice = new HashMap<>();

        valuesByName.forEach((name, value) -> {
            deviceService.findByName(name).ifPresentOrElse(
                    device -> valuesByDevice.put(device, value),
                    () -> throwUnknownDeviceException(name)
            );
        });

        validateDevicesCount(valuesByDevice);
        validateValues(valuesByDevice);

        meterReadingsService.createMeterReadingRecord(user, valuesByDevice, LocalDate.now());

        return "Показания счётчика успешно внесены";
    }

    private void throwUnknownDeviceException(String name) {
        throw new GenericHttpException(HttpServletResponse.SC_CONFLICT, "Устройство '%s' не существует".formatted(name));
    }

    private void validateMetricsExistence(User user) {
        if (meterReadingsService.checkMetricReadingRecordExistence(user)) {
            throw new GenericHttpException(HttpServletResponse.SC_CONFLICT, "Вы уже добавили показания счётчиков в этом месяце");
        }
    }

    private void validateDevicesCount(Map<Device, Double> values) {
        final List<Device> availableDevices = deviceService.listAvailableDevices();
        if (values.size() != availableDevices.size()) {
            throw new GenericHttpException(HttpServletResponse.SC_CONFLICT, "Количество введенных устройств не соответствует доступным устройствам");
        }
    }

    private void validateValues(Map<Device, Double> values) {
        for (Double value : values.values()) {
            if (value <= 0) {
                throw new GenericHttpException(HttpServletResponse.SC_CONFLICT, "Введённые показания должны быть больше 0");
            }
        }
    }
}


