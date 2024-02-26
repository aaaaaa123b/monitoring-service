package by.harlap.monitoring.in.controller.openapi;

import by.harlap.monitoring.dto.ErrorResponse;
import by.harlap.monitoring.dto.TokenResponseDto;
import by.harlap.monitoring.dto.user.AuthenticationUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "The authentication API")
public interface AuthenticationOpenAPI {

    @Operation(summary = "Authorize", tags = "Authentication",
            requestBody = @RequestBody(description = "RequestBody for AuthenticationUserDto",
                    content = @Content(schema = @Schema(implementation = AuthenticationUserDto.class),
                            examples = @ExampleObject("""
                                    {
                                       "username" : "user",
                                       "password" : "user"
                                     }
                                     """))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authorize successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDto.class), examples = @ExampleObject("""
                               {
                                  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InVzZXIiLCJleHAiOjE3MDg5MTI3NjEsImlhdCI6MTcwODkwOTE2MSwidXNlcm5hbWUiOiJ1c2VyIn0.eog8ZKTN0QX0puURo_TMuurLmKgl-myIzj63YEbmISw"
                                }
                            """))),
            @ApiResponse(responseCode = "400", description = "Incorrectly entered data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                      "messages": [
                                        "Имя пользователя должно быть от 2 до 255 символов",
                                        "Пароль должен быть от 3 до 255 символов"
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "401", description = "There is no user with that name",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                      "messages": [
                                        "Пользователя с таким именем не существует"
                                      ]
                                    }
                                    """)))
    })
    TokenResponseDto authorize(@RequestBody @Valid AuthenticationUserDto dto);

    @Operation(summary = "Registry", tags = "Authentication",
            requestBody = @RequestBody(description = "RequestBody for AuthenticationUserDto",
                    content = @Content(schema = @Schema(implementation = AuthenticationUserDto.class),
                            examples = @ExampleObject("""
                                    {
                                       "username" : "newUser",
                                       "password" : "password"
                                     }
                                     """))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User register successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDto.class), examples = @ExampleObject("""
                               {
                                  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6InVzZXIiLCJleHAiOjE3MDg5MTI3NjEsImlhdCI6MTcwODkwOTE2MSwidXNlcm5hbWUiOiJ1c2VyIn0.eog8ZKTN0QX0puURo_TMuurLmKgl-myIzj63YEbmISw"
                                }
                            """))),
            @ApiResponse(responseCode = "400", description = "Incorrectly entered data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                      "messages": [
                                        "Имя пользователя должно быть от 2 до 255 символов",
                                        "Пароль должен быть от 3 до 255 символов"
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "401", description = "A user with that name already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                      "messages": [
                                        "Пользователь с таким именем уже существует."
                                      ]
                                    }
                                    """)))
    })
    TokenResponseDto register(@RequestBody @Valid AuthenticationUserDto authenticationUserDto);
}
