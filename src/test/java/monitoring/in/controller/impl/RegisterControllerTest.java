package monitoring.in.controller.impl;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.RequestDispatcher;
import by.harlap.monitoring.in.controller.impl.MainMenuController;
import by.harlap.monitoring.in.controller.impl.RegisterController;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.service.AuthService;
import by.harlap.monitoring.util.ConsoleDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private ConsoleDecorator console;

    @Mock
    private RequestDispatcher dispatcher;

    private RegisterController registerController;

    private User createdUser;

    @Mock
    private MainMenuController mainMenuController;

    @BeforeEach
    public void prepareController() {
        createdUser = new User("test", "test", Role.USER);

        final ApplicationContext context = new ApplicationContext();
        context.setActiveUser(null);

        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(console, context, dispatcher);

        when(dispatcher.getController(MainMenuController.class)).thenReturn(mainMenuController);

        registerController = new RegisterController(initializationData, authService);
    }

    @Test
    void testShow() {
        when(console.readLine()).thenReturn("test", "test");
        when(authService.register("test", "test")).thenReturn(createdUser);

        registerController.show();

        verify(mainMenuController, times(1)).show();
    }
}