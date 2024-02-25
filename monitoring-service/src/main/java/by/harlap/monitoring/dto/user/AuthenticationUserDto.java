package by.harlap.monitoring.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * The AuthenticationUserDto class represents a DTO (Data Transfer Object) for authenticating users.
 * It contains information about the username and password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationUserDto {

    @NotBlank(message = "Имя пользователя не должно быть пустым")
    @Size(min = 2, max = 255, message = "Имя пользователя должно быть от 2 до 255 символов")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 3, max = 255, message = "Пароль должен быть от 3 до 255 символов")
    private String password;
}
