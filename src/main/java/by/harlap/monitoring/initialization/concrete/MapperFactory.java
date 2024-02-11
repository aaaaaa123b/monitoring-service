package by.harlap.monitoring.initialization.concrete;

import by.harlap.monitoring.mapper.*;

import java.util.HashMap;
import java.util.Map;

public class MapperFactory {

    private final Map<Class<?>, Object> mappers = new HashMap<>();

    /**
     * Constructor for RepositoryFactory. Registers various repositories with their corresponding implementations.
     */
    public MapperFactory() {
        final UserMapper userMapper = new UserMapperImpl();
        final DeviceMapper deviceMapper = new DeviceMapperImpl();
        final MeterReadingRecordMapper meterReadingRecordMapper = new MeterReadingRecordMapperImpl();
        final UserEventMapper userEventMapper = new UserEventMapperImpl();

        mappers.put(UserMapper.class, userMapper);
        mappers.put(DeviceMapper.class, deviceMapper);
        mappers.put(MeterReadingRecordMapper.class, meterReadingRecordMapper);
        mappers.put(UserEventMapper.class, userEventMapper);
    }

    /**
     * Retrieves a registered mapper based on its interface/class.
     *
     * @param mapperClass the interface/class of the repository to retrieve
     * @param <T>             the type of the mapper
     * @return the instance of the mapper, or {@code null} if not found
     */
    public <T> T findMapper(Class<? extends T> mapperClass) {
        return (T) mappers.get(mapperClass);
    }
}