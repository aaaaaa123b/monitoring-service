package by.harlap.monitoring;

import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.in.controller.impl.*;
import by.harlap.monitoring.service.InitializationService;

/**
 * The MonitoringServiceApplication class is the entry point of application.
 */
public class MonitoringServiceApplication {

    /**
     * The main method serves as the entry point for the MonitoringServiceApplication.
     * It initializes the application by creating a default user using the InitializationService,
     * and then shows the welcome screen using the WelcomeController.
     */
    public static void main(String[] args) {
        DependencyFactory.findService(InitializationService.class).createDefaultUser();
        DependencyFactory.findController(WelcomeController.class).show();
    }
}
