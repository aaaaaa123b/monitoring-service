package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.RequestDispatcher;
import by.harlap.monitoring.in.controller.impl.*;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.ConsoleDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainMenuControllerTest {

    @Mock
    private ConsoleDecorator console;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private MeterReadingsRelevantInfoController meterReadingsRelevantInfoController;

    @Mock
    private MeterReadingsInputController meterReadingsInputController;

    @Mock
    private MeterReadingsMonthlyInfoController meterReadingsMonthlyInfoController;

    @Mock
    private MeterReadingsHistoryController meterReadingsHistoryController;

    private User activeUser;

    private MainMenuController mainMenuController;

    @BeforeEach
    public void prepareController() {
        activeUser = new User("test", "test", Role.USER);

        final ApplicationContext context = new ApplicationContext();
        context.setActiveUser(activeUser);

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(console, context, dispatcher);
        mainMenuController = new MainMenuController(initializationData);
    }

    @Test
    void testShowAndNavigateToRelevantInfoController() {
        when(dispatcher.getController(MeterReadingsRelevantInfoController.class)).thenReturn(meterReadingsRelevantInfoController);
        when(console.readInt()).thenReturn(1, 5);

        mainMenuController.show();

        verify(meterReadingsRelevantInfoController, times(1)).show();
    }

    @Test
    void testShowAndNavigateToInputController() {
        when(dispatcher.getController(MeterReadingsInputController.class)).thenReturn(meterReadingsInputController);
        when(console.readInt()).thenReturn(2, 5);

        mainMenuController.show();

        verify(meterReadingsInputController, times(1)).show();
    }

    @Test
    void testShowAndNavigateToMonthlyInfoController() {
        when(dispatcher.getController(MeterReadingsMonthlyInfoController.class)).thenReturn(meterReadingsMonthlyInfoController);
        when(console.readInt()).thenReturn(3, 5);

        mainMenuController.show();

        verify(meterReadingsMonthlyInfoController, times(1)).show();
    }

    @Test
    void testShowAndNavigateToHistoryController() {
        when(dispatcher.getController(MeterReadingsHistoryController.class)).thenReturn(meterReadingsHistoryController);
        when(console.readInt()).thenReturn(4, 5);

        mainMenuController.show();

        verify(meterReadingsHistoryController, times(1)).show();
    }

    @Test
    void testShowAndLogout() {
        when(console.readInt()).thenReturn(5);

        mainMenuController.show();
    }
}