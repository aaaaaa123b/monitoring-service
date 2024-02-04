package by.harlap.monitoring.service;

/**
 * The InitializationService interface defines a method for initializing the application,
 * specifically for creating a default user during application startup.
 */
public interface InitializationService {

    /**
     * Creates a default user during application initialization.
     * This method is responsible for setting up necessary defaults, such as creating an initial user,
     * to ensure the application starts with essential configurations.
     */
    void createDefaultUser();
}
