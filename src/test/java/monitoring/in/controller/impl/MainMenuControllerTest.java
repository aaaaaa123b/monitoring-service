package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.impl.*;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.ConsoleDecorator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for UserMainMenuController")
class MainMenuControllerTest {

    @Mock
    private ConsoleDecorator console;

    @Mock
    private MeterReadingsRelevantInfoController meterReadingsRelevantInfoController;

    @Mock
    private MeterReadingsInputController meterReadingsInputController;

    @Mock
    private MeterReadingsMonthlyInfoController meterReadingsMonthlyInfoController;

    @Mock
    private MeterReadingsHistoryController meterReadingsHistoryController;

    private UserMainMenuController mainMenuController;

    private static MockedStatic<DependencyFactory> factoryMockedStatic;

    @BeforeAll
    public static void init() {
        factoryMockedStatic = mockStatic(DependencyFactory.class);
    }

    @BeforeEach
    public void prepareController() {
        User activeUser = new User("liquibase", "liquibase", Role.USER);

        final ApplicationContext context = new ApplicationContext();
        context.setActiveUser(activeUser);

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(console, context);
        mainMenuController = new UserMainMenuController(initializationData);
    }

    @Test
    @DisplayName("UserMainMenuController should redirect to the method show of MeterReadingsRelevantInfoController")
    void showAndNavigateToRelevantInfoControllerTest() {
        when(DependencyFactory.findController(MeterReadingsRelevantInfoController.class)).thenReturn(meterReadingsRelevantInfoController);
        when(console.readInt()).thenReturn(1, 5);

        mainMenuController.show();

        verify(meterReadingsRelevantInfoController, times(1)).show();
    }

    @Test
    @DisplayName("UserMainMenuController should redirect to the method show of MeterReadingsInputController")
    void showAndNavigateToInputControllerTest() {
        when(DependencyFactory.findController(MeterReadingsInputController.class)).thenReturn(meterReadingsInputController);
        when(console.readInt()).thenReturn(2, 5);

        mainMenuController.show();

        verify(meterReadingsInputController, times(1)).show();
    }

    @Test
    @DisplayName("UserMainMenuController should redirect to the method show of MeterReadingsMonthlyInfoController")
    void showAndNavigateToMonthlyInfoControllerTest() {
        when(DependencyFactory.findController(MeterReadingsMonthlyInfoController.class)).thenReturn(meterReadingsMonthlyInfoController);
        when(console.readInt()).thenReturn(3, 5);

        mainMenuController.show();

        verify(meterReadingsMonthlyInfoController, times(1)).show();
    }

    @Test
    @DisplayName("UserMainMenuController should redirect to the method show of MeterReadingsHistoryController")
    void showAndNavigateToHistoryControllerTest() {
        when(DependencyFactory.findController(MeterReadingsHistoryController.class)).thenReturn(meterReadingsHistoryController);
        when(console.readInt()).thenReturn(4, 5);

        mainMenuController.show();

        verify(meterReadingsHistoryController, times(1)).show();
    }

    @AfterAll
    public static void close() {
        factoryMockedStatic.close();
    }
}