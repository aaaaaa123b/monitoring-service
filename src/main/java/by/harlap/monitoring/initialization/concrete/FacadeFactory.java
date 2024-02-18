package by.harlap.monitoring.initialization.concrete;

import by.harlap.monitoring.facade.*;
import by.harlap.monitoring.mapper.DeviceMapper;
import by.harlap.monitoring.mapper.MeterReadingRecordMapper;
import by.harlap.monitoring.mapper.UserEventMapper;
import by.harlap.monitoring.mapper.UserMapper;
import by.harlap.monitoring.service.*;
import by.harlap.monitoring.validator.MeterReadingInputValidator;
import by.harlap.monitoring.validator.MeterReadingsMonthlyInfoValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * The FacadeFactory class manages the registration and retrieval of facades used in the application.
 * It provides methods to register facades and retrieve them based on their interfaces/classes.
 */
public class FacadeFactory {

    private final Map<Class<?>, Object> facades = new HashMap<>();

    /**
     * Constructor for FacadeFactory. Registers various facades with their corresponding implementations.
     */
    public FacadeFactory(MapperFactory mapperFactory, ServiceFactory serviceFactory, ValidatorFactory validatorFactory) {
        final UserEventMapper userEventMapper = mapperFactory.findMapper(UserEventMapper.class);
        final DeviceMapper deviceMapper = mapperFactory.findMapper(DeviceMapper.class);
        final MeterReadingRecordMapper meterReadingRecordMapper = mapperFactory.findMapper(MeterReadingRecordMapper.class);
        final UserMapper userMapper = mapperFactory.findMapper(UserMapper.class);

        final AuthService authService = serviceFactory.findService(AuthService.class);
        final AuditService auditService = serviceFactory.findService(AuditService.class);
        final DeviceService deviceService = serviceFactory.findService(DeviceService.class);
        final MeterReadingsService meterReadingsService = serviceFactory.findService(MeterReadingsService.class);

        final MeterReadingInputValidator meterReadingInputValidator = validatorFactory.findValidator(MeterReadingInputValidator.class);
        final MeterReadingsMonthlyInfoValidator meterReadingsMonthlyInfoValidator = validatorFactory.findValidator(MeterReadingsMonthlyInfoValidator.class);

        final AuditFacade auditFacade = new AuditFacade(userEventMapper, auditService);
        final AuthFacade authFacade = new AuthFacade(authService);
        final DeviceFacade deviceFacade = new DeviceFacade(deviceMapper, deviceService);
        final MeterReadingInputFacade meterReadingInputFacade = new MeterReadingInputFacade(meterReadingsService, deviceService, meterReadingInputValidator);
        final MeterReadingsHistoryFacade meterReadingsHistoryFacade = new MeterReadingsHistoryFacade(meterReadingsService, deviceService, meterReadingRecordMapper);
        final MeterReadingsMonthlyInfoFacade meterReadingsMonthlyInfoFacade = new MeterReadingsMonthlyInfoFacade(meterReadingsService, deviceService, meterReadingRecordMapper, meterReadingsMonthlyInfoValidator);
        final MeterReadingsRelevantInfoFacade meterReadingsRelevantInfoFacade = new MeterReadingsRelevantInfoFacade(meterReadingsService, deviceService, meterReadingRecordMapper);
        final RegisterFacade registerFacade = new RegisterFacade(authService, userMapper);

        facades.put(AuditFacade.class, auditFacade);
        facades.put(AuthFacade.class, authFacade);
        facades.put(DeviceFacade.class, deviceFacade);
        facades.put(MeterReadingInputFacade.class, meterReadingInputFacade);
        facades.put(MeterReadingsHistoryFacade.class, meterReadingsHistoryFacade);
        facades.put(MeterReadingsMonthlyInfoFacade.class, meterReadingsMonthlyInfoFacade);
        facades.put(MeterReadingsRelevantInfoFacade.class, meterReadingsRelevantInfoFacade);
        facades.put(RegisterFacade.class, registerFacade);
    }

    /**
     * Retrieves a registered facade based on its interface/class.
     *
     * @param facadeClass the interface/class of the repository to retrieve
     * @param <T>         the type of the repository
     * @return the instance of the facade
     */
    public <T> T findFacade(Class<? extends T> facadeClass) {
        return (T) facades.get(facadeClass);
    }
}
