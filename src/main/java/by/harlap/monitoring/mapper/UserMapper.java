package by.harlap.monitoring.mapper;

import by.harlap.monitoring.dto.user.RegisterUserDto;
import by.harlap.monitoring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The UserMapper interface provides methods for mapping between User entities and RegisterUserDto objects.
 */
@Mapper
public interface UserMapper {

    /**
     * Maps a User entity to a RegisterUserDto object.
     *
     * @param user the User entity to map
     * @return the corresponding RegisterUserDto object
     */
    RegisterUserDto toDto(User user);

    /**
     * Maps a RegisterUserDto object to a User entity.
     *
     * @param registerUserDto the RegisterUserDto object to map
     * @return the corresponding User entity
     */
    @Mapping(target = "role", expression = "java(by.harlap.monitoring.enumeration.Role.USER)")
    User toEntity(RegisterUserDto registerUserDto);
}
