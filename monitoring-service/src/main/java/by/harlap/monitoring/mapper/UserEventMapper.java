package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.model.UserEvent;
import org.mapstruct.Mapper;

/**
 * The UserEventMapper interface provides methods for mapping between UserEvent entities and UserEventResponseDto objects.
 */
@Mapper
public interface UserEventMapper {

    /**
     * Maps a UserEvent entity to a UserEventResponseDto object.
     *
     * @param userEvent the UserEvent entity to map
     * @return the corresponding UserEventResponseDto object
     */
    UserEventResponseDto toDto(UserEvent userEvent);
}
