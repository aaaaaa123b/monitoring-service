package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.impl.LoginController;
import by.harlap.monitoring.in.controller.impl.RegisterController;
import by.harlap.monitoring.in.controller.impl.WelcomeController;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.util.ConsoleDecorator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for WelcomeController")
class WelcomeControllerTest {

    @Mock
    private ConsoleDecorator console;

    @Mock
    private LoginController loginController;

    @Mock
    private RegisterController registerController;

    private WelcomeController welcomeController;

    private static MockedStatic<DependencyFactory> factoryMockedStatic;

    @BeforeAll
    public static void init() {
        factoryMockedStatic = mockStatic(DependencyFactory.class);
    }

    @BeforeEach
    public void prepareController() {
        final ApplicationContext context = new ApplicationContext();

        when(DependencyFactory.findController(LoginController.class)).thenReturn(loginController);
        when(DependencyFactory.findController(RegisterController.class)).thenReturn(registerController);

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(console, context);
        welcomeController = new WelcomeController(initializationData);
    }

    @Test
    @DisplayName("Should call the method of LoginController when user selects option 1")
    void showLoginController() {
        when(console.readInt()).thenReturn(1);

        welcomeController.show();

        verify(loginController, times(1)).show();
    }

    @Test
    @DisplayName("Should call the method of RegisterController when user selects option 2")
    void showRegisterController() {
        when(console.readInt()).thenReturn(2);

        welcomeController.show();

        verify(registerController, times(1)).show();
    }

    @AfterAll
    public static void close() {
        factoryMockedStatic.close();
    }
}