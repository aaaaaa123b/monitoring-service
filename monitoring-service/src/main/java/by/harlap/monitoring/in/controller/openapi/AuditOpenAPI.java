package by.harlap.monitoring.in.controller.openapi;

import by.harlap.monitoring.dto.ErrorResponse;
import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

/**
 * Interface representing the OpenAPI specification for AuditController.
 */
@Tag(name = "Audit", description = "The audit API")
public interface AuditOpenAPI {

    @Operation(summary = "Find all audit", tags = "Audit",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audit retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEventResponseDto.class), examples = @ExampleObject("""
                            [
                               {
                                 "date": "2024-01-01",
                                 "userId": 2,
                                 "action": "Тестовое действие"
                               },
                               {
                                 "date": "2024-02-01",
                                 "userId": 2,
                                 "action": "Тестовое действие"
                               },
                               {
                                 "date": "2024-03-01",
                                 "userId": 2,
                                 "action": "Тестовое действие"
                               }
                             ]
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
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject("""
                            {
                              "messages": [
                                "Доступ запрещен"
                              ]
                            }
                            """)))
    })
    List<UserEventResponseDto> findAllAudit(@RequestAttribute("username") String username);
}
