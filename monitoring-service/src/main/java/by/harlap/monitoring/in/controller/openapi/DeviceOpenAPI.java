package by.harlap.monitoring.in.controller.openapi;

import by.harlap.monitoring.dto.ErrorResponse;
import by.harlap.monitoring.dto.device.CreateDeviceDto;
import by.harlap.monitoring.dto.device.DeviceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestAttribute;

@Tag(name = "Device", description = "The device API")
public interface DeviceOpenAPI {

    @Operation(summary = "Save new device", tags = "Device",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            requestBody = @RequestBody(description = "RequestBody for CreateDeviceDto",
                    content = @Content(schema = @Schema(implementation = CreateDeviceDto.class),
                            examples = @ExampleObject("""
                                    {
                                      "name" : "newDevice"
                                    }
                                     """))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device save successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DeviceResponseDto.class), examples = @ExampleObject("""
                               {
                                 "id": 4,
                                 "name": "newDevice"
                               }
                            """))),
            @ApiResponse(responseCode = "400", description = "Incorrectly entered data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                       "messages": [
                                         "Имя устройства должно быть от 3 до 255 символов"
                                       ]
                                     }
                                    """))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject("""
                            {
                              "messages": [
                                "Неверный JWT"
                              ]
                            }
                            """))),
            @ApiResponse(responseCode = "409", description = "A device with that name already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject("""
                                    {
                                       "messages": [
                                         "Устройство с таким именем уже существует"
                                       ]
                                     }
                                    """)))
    })
    DeviceResponseDto saveDevice(@RequestAttribute("username") String username, @RequestBody @Valid CreateDeviceDto deviceDto);
}
