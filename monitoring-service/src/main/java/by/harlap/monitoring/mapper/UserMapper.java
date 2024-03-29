package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import by.harlap.monitoring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The UserMapper interface provides methods for mapping between User entities and AuthenticationUserDto objects.
 */
@Mapper
public interface UserMapper {

    /**
     * Maps a AuthenticationUserDto object to a User entity.
     *
     * @param authenticationUserDto the AuthenticationUserDto object to map
     * @return the corresponding User entity
     */
    @Mapping(target = "role", expression = "java(by.harlap.monitoring.enumeration.Role.USER)")
    User toUserEntity(AuthenticationUserDto authenticationUserDto);
}
