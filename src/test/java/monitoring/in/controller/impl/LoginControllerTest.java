package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.impl.LoginController;
import by.harlap.monitoring.in.controller.impl.UserMainMenuController;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuthService;
import by.harlap.monitoring.util.ConsoleDecorator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for LoginController")
class LoginControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private ConsoleDecorator console;

    @Mock
    private UserMainMenuController mainMenuController;

    private LoginController loginController;
    private User activeUser;
    private static MockedStatic<DependencyFactory> factoryMockedStatic;

    @BeforeAll
    public static void init() {
        factoryMockedStatic = mockStatic(DependencyFactory.class);
    }

    @BeforeEach
    public void prepareController() {
        activeUser = new User("liquibase", "liquibase", Role.USER);

        final ApplicationContext context = new ApplicationContext();
        context.setActiveUser(activeUser);

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(console, context);

        when(DependencyFactory.findController(UserMainMenuController.class)).thenReturn(mainMenuController);

        loginController = new LoginController(initializationData, authService);
    }

    @Test
    @DisplayName("Login Controller should show main menu after successful login ")
    void showTest() {
        when(console.readLine()).thenReturn("liquibase", "liquibase");
        when(authService.login("liquibase", "liquibase")).thenReturn(activeUser);

        loginController.show();
        verify(mainMenuController, times(1)).show();
    }

    @AfterAll
    public static void close() {
        factoryMockedStatic.close();
    }
}
