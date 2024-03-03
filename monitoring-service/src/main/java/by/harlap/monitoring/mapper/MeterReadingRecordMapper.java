package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import by.harlap.monitoring.model.MeterReadingRecord;
import org.mapstruct.Mapper;

/**
 * The MeterReadingRecordMapper interface provides methods for mapping between MeterReadingRecord entities and MeterReadingResponseDto objects.
 */
@Mapper
public interface MeterReadingRecordMapper {

    /**
     * Maps a MeterReadingRecord entity to a MeterReadingResponseDto object.
     *
     * @param meterReadingRecord the MeterReadingRecord entity to map
     * @return the corresponding MeterReadingResponseDto object
     */
    MeterReadingResponseDto toMeterReadingResponseDto(MeterReadingRecord meterReadingRecord);

    /**
     * Maps a CreateMeterReadingDto object to a MeterReadingRecord entity.
     *
     * @param meterReadingRecord the CreateMeterReadingDto object to map
     * @return the corresponding MeterReadingRecord entity
     */
    MeterReadingRecord toMeterReadingEntity(CreateMeterReadingDto meterReadingRecord);
}
