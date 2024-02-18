package by.harlap.monitoring.initialization.concrete;

import by.harlap.monitoring.service.DeviceService;
import by.harlap.monitoring.service.MeterReadingsService;
import by.harlap.monitoring.validator.MeterReadingInputValidator;
import by.harlap.monitoring.validator.MeterReadingsMonthlyInfoValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * The ValidatorFactory class manages the registration and retrieval of validators used in the application.
 * It provides methods to register validators and retrieve them based on their interfaces/classes.
 */
public class ValidatorFactory {

    private final Map<Class<?>, Object> validators = new HashMap<>();

    /**
     * Constructor for ValidatorFactory. Registers various validators with their corresponding implementations.
     */
    public ValidatorFactory(ServiceFactory serviceFactory) {
        final MeterReadingsService meterReadingsService = serviceFactory.findService(MeterReadingsService.class);
        final DeviceService deviceService = serviceFactory.findService(DeviceService.class);

        final MeterReadingInputValidator meterReadingInputValidator = new MeterReadingInputValidator(meterReadingsService, deviceService);
        final MeterReadingsMonthlyInfoValidator meterReadingsMonthlyInfoValidator = new MeterReadingsMonthlyInfoValidator();
        validators.put(MeterReadingInputValidator.class, meterReadingInputValidator);
        validators.put(MeterReadingsMonthlyInfoValidator.class, meterReadingsMonthlyInfoValidator);
    }

    /**
     * Retrieves a registered validator based on its interface/class.
     *
     * @param validatorClass the interface/class of the validator to retrieve
     * @param <T>            the type of the validator
     * @return the instance of the validator, or {@code null} if not found
     */
    public <T> T findValidator(Class<? extends T> validatorClass) {
        return (T) validators.get(validatorClass);
    }
}

