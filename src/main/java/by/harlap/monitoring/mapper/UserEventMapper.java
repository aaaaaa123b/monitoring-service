package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.userEvent.UserEventDto;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

/**
 * The UserEventMapper interface provides methods for mapping between UserEvent entities and UserEventDto objects.
 */
@Mapper
public interface UserEventMapper {

    /**
     * Maps a UserEvent entity to a UserEventDto object.
     *
     * @param userEvent the UserEvent entity to map
     * @return the corresponding UserEventDto object
     */
    @Mapping(target = "userName", expression = "java(findUsername(userEvent.getUserId()))")
    UserEventDto toDto(UserEvent userEvent);

    default String findUsername(Long userId) {
        UserService userService = DependencyFactory.findService(UserService.class);
        Optional<User> user = userService.findUserById(userId);
        return user.map(User::getUsername).orElse(null);
    }

    /**
     * Maps a UserEventDto object to a UserEvent entity.
     *
     * @param userEventDto the UserEventDto object to map
     * @return the corresponding UserEvent entity
     */
    @Mapping(target = "id", expression = "java(findId(userEventDto.getUserName()))")
    UserEvent toEntity(UserEventDto userEventDto);

    default Long findId(String username) {
        UserService userService = DependencyFactory.findService(UserService.class);
        Optional<User> user = userService.findUserByUsername(username);
        return user.map(User::getId).orElse(null);
    }
}
