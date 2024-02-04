package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.impl.MeterReadingsRelevantInfoController;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.MeterReadingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for MeterReadingsRelevantInfoController")
class MeterReadingsRelevantInfoControllerTest {

    @Mock
    private MeterReadingsService meterReadingsService;

    private User activeUser;

    private MeterReadingsRelevantInfoController meterReadingsRelevantInfoController;

    @BeforeEach
    public void prepareController() {
        activeUser = new User("test", "test", Role.USER);

        final ApplicationContext context = new ApplicationContext();
        context.setActiveUser(activeUser);

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(null, context);
        meterReadingsRelevantInfoController = new MeterReadingsRelevantInfoController(initializationData, meterReadingsService);
    }

    @Test
    @DisplayName("Should display relevant meter readings information")
    void show() {
        when(meterReadingsService.findRelevantRecords(activeUser)).thenReturn(List.of());

        meterReadingsRelevantInfoController.show();

        verify(meterReadingsService, times(1)).findRelevantRecords(activeUser);
    }
}