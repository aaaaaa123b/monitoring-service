package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.impl.MeterReadingsHistoryController;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for MeterReadingsHistoryController")
class MeterReadingsHistoryControllerTest {

    @Mock
    private MeterReadingsService meterReadingsService;
    @Mock
    private DeviceService deviceService;
    @Mock
    private UserService userService;

    private User activeUser;

    private MeterReadingsHistoryController meterReadingsHistoryController;

    @BeforeEach
    public void prepareController() {
        activeUser = new User("test", "test", Role.USER);

        final ApplicationContext context = new ApplicationContext();
        context.setActiveUser(activeUser);

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(null, context);
        meterReadingsHistoryController = new MeterReadingsHistoryController(initializationData, meterReadingsService, deviceService);
    }

    @Test
    @DisplayName("Test should retrieve and show meter readings history for activeUser")
    void show() {
        when(meterReadingsService.findAllRecords(activeUser)).thenReturn(List.of());

        meterReadingsHistoryController.show();

        verify(meterReadingsService, times(1)).findAllRecords(activeUser);
    }
}