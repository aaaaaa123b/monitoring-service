package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.model.UserEvent;
import org.mapstruct.Mapper;

/**
 * The UserEventMapper interface provides methods for mapping between UserEvent entities and UserEventResponseDto objects.
 */
@Mapper(componentModel = "spring")
public interface UserEventMapper {


    UserEventResponseDto toDto(UserEvent userEvent);

    /**
     * Maps a UserEventResponseDto object to a UserEvent entity.
     *
     * @param userEventResponseDto the UserEventResponseDto object to map
     * @return the corresponding UserEvent entity
     */
    UserEvent toEntity(UserEventResponseDto userEventResponseDto);

}
