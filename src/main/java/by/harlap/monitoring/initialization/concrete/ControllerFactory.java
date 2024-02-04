package by.harlap.monitoring.initialization.concrete;

import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.impl.*;
import by.harlap.monitoring.service.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The ControllerFactory class manages the registration and retrieval of controllers used in request processing.
 * It provides methods to register controllers and retrieve them based on their class.
 */
public class ControllerFactory {

    private final Map<Class<?>, Object> controllers = new HashMap<>();

    /**
     * Constructor for ControllerFactory. Registers various controllers with the provided services.
     *
     * @param initializationData The initialization data for controllers.
     * @param serviceFactory      The factory for retrieving service instances.
     */
    public ControllerFactory(AbstractController.InitializationData initializationData, ServiceFactory serviceFactory) {
        final AuthService authService = serviceFactory.findService(AuthService.class);
        final AuditService auditService = serviceFactory.findService(AuditService.class);
        final DeviceService deviceService = serviceFactory.findService(DeviceService.class);
        final MeterReadingsService meterReadingsService = serviceFactory.findService(MeterReadingsService.class);
        final UserService userService = serviceFactory.findService(UserService.class);

        final LoginController loginController = new LoginController(initializationData, authService);
        final RegisterController registerController = new RegisterController(initializationData, authService);
        final WelcomeController welcomeController = new WelcomeController(initializationData);
        final UserMainMenuController userMainMenuController = new UserMainMenuController(initializationData);
        final AdminMainMenuController adminMainMenuController = new AdminMainMenuController(initializationData);
        final MeterReadingsRelevantInfoController meterReadingsInfoController = new MeterReadingsRelevantInfoController(initializationData, meterReadingsService, deviceService);
        final MeterReadingsInputController meterReadingsInputController = new MeterReadingsInputController(initializationData, meterReadingsService, deviceService);
        final MeterReadingsMonthlyInfoController meterReadingsMonthlyInfoController = new MeterReadingsMonthlyInfoController(initializationData, meterReadingsService, deviceService);
        final MeterReadingsHistoryController meterReadingsHistoryController = new MeterReadingsHistoryController(initializationData, meterReadingsService, deviceService);
        final AuditController auditController = new AuditController(initializationData, auditService, userService);
        final AddNewDeviceController addController = new AddNewDeviceController(initializationData, deviceService);

        registerController(loginController);
        registerController(registerController);
        registerController(welcomeController);
        registerController(userMainMenuController);
        registerController(adminMainMenuController);
        registerController(meterReadingsInfoController);
        registerController(meterReadingsInputController);
        registerController(meterReadingsMonthlyInfoController);
        registerController(meterReadingsHistoryController);
        registerController(auditController);
        registerController(addController);
    }

    private void registerController(AbstractController controller) {
        controllers.put(controller.getClass(), controller);
    }

    /**
     * Retrieves a registered controller based on its class.
     *
     * @param controllerClass The class of the controller to retrieve.
     * @return The instance of the controller, or {@code null} if not found.
     */
    public <T> T getController(Class<? extends T> controllerClass) {
        return (T) controllers.get(controllerClass);
    }
}
