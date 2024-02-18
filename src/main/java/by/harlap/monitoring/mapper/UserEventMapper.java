package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

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
    @Mapping(target = "userName", expression = "java(findUsername(userEvent.getUserId()))")
    UserEventResponseDto toDto(UserEvent userEvent);

    default String findUsername(Long userId) {
        UserService userService = DependencyFactory.findService(UserService.class);
        Optional<User> user = userService.findUserById(userId);
        return user.map(User::getUsername).orElse(null);
    }

    /**
     * Maps a UserEventResponseDto object to a UserEvent entity.
     *
     * @param userEventResponseDto the UserEventResponseDto object to map
     * @return the corresponding UserEvent entity
     */
    @Mapping(target = "id", expression = "java(findId(userEventResponseDto.getUserName()))")
    UserEvent toEntity(UserEventResponseDto userEventResponseDto);

    default Long findId(String username) {
        UserService userService = DependencyFactory.findService(UserService.class);
        Optional<User> user = userService.findUserByUsername(username);
        return user.map(User::getId).orElse(null);
    }
}
