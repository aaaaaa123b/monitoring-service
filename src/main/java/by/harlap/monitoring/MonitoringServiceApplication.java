package by.harlap.monitoring;

import by.harlap.monitoring.config.ApplicationContext;
import by.harlap.monitoring.in.controller.AbstractController;
import by.harlap.monitoring.in.controller.RequestDispatcher;
import by.harlap.monitoring.in.controller.impl.*;
import by.harlap.monitoring.repository.AuditRepository;
import by.harlap.monitoring.repository.DeviceRepository;
import by.harlap.monitoring.repository.MetricsRecordRepository;
import by.harlap.monitoring.repository.UserRepository;
import by.harlap.monitoring.repository.impl.InMemoryAuditRepository;
import by.harlap.monitoring.repository.impl.InMemoryDeviceRepository;
import by.harlap.monitoring.repository.impl.InMemoryMetricsRecordRepository;
import by.harlap.monitoring.repository.impl.InMemoryUserRepository;
import by.harlap.monitoring.service.*;
import by.harlap.monitoring.service.impl.*;
import by.harlap.monitoring.util.ConsoleDecorator;

/**
 * The MonitoringServiceApplication class is the entry point of application.
 */
public class MonitoringServiceApplication {

    public static void main(String[] args) {
        final ConsoleDecorator console = new ConsoleDecorator();
        final ApplicationContext context = new ApplicationContext();
        final RequestDispatcher dispatcher = new RequestDispatcher();
        final AbstractController.InitializationData initializationData = new AbstractController.InitializationData(console, context, dispatcher);

        final AuditRepository auditRepository = new InMemoryAuditRepository();
        final UserRepository userRepository = new InMemoryUserRepository();
        final DeviceRepository deviceRepository = new InMemoryDeviceRepository();
        final MetricsRecordRepository metricsRecordRepository = new InMemoryMetricsRecordRepository();

        final AuditService auditService = new DefaultAuditService(auditRepository);
        final UserService userService = new DefaultUserService(userRepository);
        final AuthService authService = new DefaultAuthService(userService, auditService);
        final MeterReadingsService meterReadingsService = new DefaultMeterReadingsService(metricsRecordRepository, auditService);
        final DeviceService deviceService = new DefaultDeviceService(deviceRepository);
        final InitializationService initializationService = new DefaultInitializationService(userService);

        final LoginController loginController = new LoginController(initializationData, authService);
        final RegisterController registerController = new RegisterController(initializationData, authService);
        final WelcomeController welcomeController = new WelcomeController(initializationData);
        final MainMenuController mainMenuController = new MainMenuController(initializationData);
        final MeterReadingsRelevantInfoController meterReadingsInfoController = new MeterReadingsRelevantInfoController(initializationData, meterReadingsService);
        final MeterReadingsInputController meterReadingsInputController = new MeterReadingsInputController(initializationData, meterReadingsService, deviceService);
        final MeterReadingsMonthlyInfoController meterReadingsMonthlyInfoController = new MeterReadingsMonthlyInfoController(initializationData, meterReadingsService);
        final MeterReadingsHistoryController meterReadingsHistoryController = new MeterReadingsHistoryController(initializationData, meterReadingsService);
        final AuditController auditController = new AuditController(initializationData, auditService);

        dispatcher.registerController(loginController);
        dispatcher.registerController(registerController);
        dispatcher.registerController(welcomeController);
        dispatcher.registerController(mainMenuController);
        dispatcher.registerController(meterReadingsInfoController);
        dispatcher.registerController(meterReadingsInputController);
        dispatcher.registerController(meterReadingsMonthlyInfoController);
        dispatcher.registerController(meterReadingsHistoryController);
        dispatcher.registerController(auditController);

        initializationService.createDefaultUser();

        welcomeController.show();
    }
}
