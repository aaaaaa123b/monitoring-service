package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.impl.MeterReadingsMonthlyInfoController;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.service.UserService;
import by.harlap.monitoring.util.ConsoleDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.time.Year;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for MeterReadingsMonthlyInfoController")
class MeterReadingsMonthlyInfoControllerTest {

    @Mock
    private ConsoleDecorator console;

    @Mock
    private MeterReadingsService meterReadingsService;

    @Mock
    DeviceService deviceService;

    @Mock
    UserService userService;

    private User activeUser;

    private MeterReadingsMonthlyInfoController meterReadingsMonthlyInfoController;

    @BeforeEach
    public void prepareController() {
        activeUser = new User(1L, "liquibase", "liquibase", Role.USER);

        final ApplicationContext context = new ApplicationContext();
        context.setActiveUser(activeUser);

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(console, context);
        meterReadingsMonthlyInfoController = new MeterReadingsMonthlyInfoController(initializationData, meterReadingsService, deviceService, userService);
    }

    @Test
    @DisplayName("Should display monthly information for specified month and year")
    void showTest() {
        final Month month = Month.JANUARY;
        final Year year = Year.of(2024);

        when(console.readInt()).thenReturn(year.getValue(), month.getValue());
        when(meterReadingsService.findRecordsForSpecifiedMonth(activeUser, month, year)).thenReturn(List.of());

        meterReadingsMonthlyInfoController.show();

        verify(meterReadingsService, times(1)).findRecordsForSpecifiedMonth(activeUser,month,year);
    }
}