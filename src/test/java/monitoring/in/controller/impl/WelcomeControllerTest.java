package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.RequestDispatcher;
import by.harlap.monitoring.in.controller.impl.LoginController;
import by.harlap.monitoring.in.controller.impl.RegisterController;
import by.harlap.monitoring.in.controller.impl.WelcomeController;
import by.harlap.monitoring.util.ConsoleDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WelcomeControllerTest {

    @Mock
    private ConsoleDecorator console;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private LoginController loginController;

    @Mock
    private RegisterController registerController;

    private WelcomeController welcomeController;

    @BeforeEach
    public void prepareController() {
        final ApplicationContext context = new ApplicationContext();

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(console, context, dispatcher);
        welcomeController = new WelcomeController(initializationData);
    }

    @Test
    void showLoginController() {
        when(dispatcher.getController(LoginController.class)).thenReturn(loginController);
        when(console.readInt()).thenReturn(1);

        welcomeController.show();

        verify(loginController, times(1)).show();
    }

    @Test
    void showRegisterController() {
        when(dispatcher.getController(RegisterController.class)).thenReturn(registerController);
        when(console.readInt()).thenReturn(2);

        welcomeController.show();

        verify(registerController, times(1)).show();
    }
}