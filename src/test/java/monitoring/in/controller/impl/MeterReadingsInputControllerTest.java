package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.impl.MeterReadingsInputController;
import by.harlap.monitoring.model.ColdWaterDevice;
import by.harlap.monitoring.model.HeatingDevice;
import by.harlap.monitoring.model.HotWaterDevice;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.base.AbstractDevice;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.util.ConsoleDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeterReadingsInputControllerTest {

    @Mock
    private ConsoleDecorator console;

    @Mock
    private MeterReadingsService meterReadingsService;

    @Mock
    private DeviceService deviceService;

    private User activeUser;

    private MeterReadingsInputController meterReadingsInputController;

    @BeforeEach
    public void prepareController() {
        activeUser = new User("test", "test", Role.USER);

        final ApplicationContext context = new ApplicationContext();
        context.setActiveUser(activeUser);

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(console, context, null);
        meterReadingsInputController = new MeterReadingsInputController(initializationData, meterReadingsService, deviceService);
    }

    @Test
    void testShow() {
        List<AbstractDevice> devices = new ArrayList<>();
        devices.add(new HeatingDevice("отопление"));
        devices.add(new ColdWaterDevice("холодная вода"));
        devices.add(new HotWaterDevice("горячая вода"));

        when(deviceService.listAvailableDevices()).thenReturn(devices);
        when(meterReadingsService.checkMetricReadingRecordExistence(activeUser)).thenReturn(false);
        when(console.readDouble()).thenReturn(3.0, 2.0, 1.0);

        meterReadingsInputController.show();

        verify(meterReadingsService, times(1)).createMeterReadingRecord(any());
    }
}