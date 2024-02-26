package by.harlap.monitoring.in.controller.openapi;

import by.harlap.monitoring.dto.ErrorResponse;
import by.harlap.monitoring.dto.meterReadingRecord.CreateMeterReadingDto;
import by.harlap.monitoring.dto.meterReadingRecord.MeterReadingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Interface representing the OpenAPI specification for MeterReadingController.
 */
@Tag(name = "MeterReading", description = "The meter reading API")
public interface MeterReadingOpenAPI {

    @Operation(summary = "Find all meter reading records", tags = "MeterReading",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All meter reading records retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeterReadingResponseDto.class), examples = @ExampleObject("""
                            [
                                 {
                                   "date": "2024-01-01",
                                   "userId": 2,
                                   "deviceId": 1,
                                   "value": 100.5
                                 },
                                 {
                                   "date": "2024-01-01",
                                   "userId": 2,
                                   "deviceId": 2,
                                   "value": 200.3
                                 },
                                 {
                                   "date": "2024-01-01",
                                   "userId": 2,
                                   "deviceId": 3,
                                   "value": 150.7
                                 },
                                 {
                                   "date": "2024-02-26",
                                   "userId": 11,
                                   "deviceId": 3,
                                   "value": 1533.13
                                 },
                                 {
                                   "date": "2024-02-26",
                                   "userId": 11,
                                   "deviceId": 2,
                                   "value": 1533.13
                                 },
                                 {
                                   "date": "2024-02-26",
                                   "userId": 11,
                                   "deviceId": 1,
                                   "value": 1533.13
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
                            """)))
    })
    List<MeterReadingResponseDto> findMeterReadingRecordsHistory(@RequestAttribute("username") String username);

    @Operation(summary = "Save meter reading records", tags = "MeterReading",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            requestBody = @RequestBody(description = "RequestBody for CreateMeterReadingDto",
                    content = @Content(schema = @Schema(implementation = CreateMeterReadingDto.class),
                            examples = @ExampleObject("""
                                    [
                                      {
                                        "deviceName": "отопление",
                                        "value": 1533.13
                                      },
                                      {
                                        "deviceName": "холодная вода",
                                        "value": 1533.13
                                      },
                                      {
                                        "deviceName": "горячая вода",
                                        "value": 1533.13
                                      }
                                    ]
                                     """))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Meter reading records save successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeterReadingResponseDto.class), examples = @ExampleObject("""
                            [
                                 {
                                   "date": "2024-02-26",
                                   "userId": 11,
                                   "deviceId": 3,
                                   "value": 1533.13
                                 },
                                 {
                                   "date": "2024-02-26",
                                   "userId": 11,
                                   "deviceId": 2,
                                   "value": 1533.13
                                 },
                                 {
                                   "date": "2024-02-26",
                                   "userId": 11,
                                   "deviceId": 1,
                                   "value": 1533.13
                                 }
                            ]
                            """))),
            @ApiResponse(responseCode = "400", description = "Incorrectly entered data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject("""
                            {
                              "messages": [
                                "Значение счётчика должно быть больше либо равно нуля"
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
            @ApiResponse(responseCode = "403", description = "Admin try to save meter reading records",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject("""
                            {
                              "messages": [
                                "Доступ запрещен"
                              ]
                            }
                            """))),
            @ApiResponse(responseCode = "409", description = "Data is not provided for all devices",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject("""
                            {
                              "messages": [
                                "Количество введенных устройств не соответствует доступным устройствам"
                              ]
                            }
                            """)))
    })
    List<MeterReadingResponseDto> saveMeterReadingRecords(@RequestAttribute("username") String username, @RequestBody @Valid List<CreateMeterReadingDto> meterReadingsDto);

    @Operation(summary = "Find all meter reading records for month", tags = "MeterReading",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            parameters = {
                    @Parameter(name = "month", description = "Enter month here", example = "1"),
                    @Parameter(name = "year", description = "Enter year here", example = "2024")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All meter reading records for specified month retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeterReadingResponseDto.class), examples = @ExampleObject("""
                            [
                                   {
                                     "date": "2024-01-01",
                                     "userId": 2,
                                     "deviceId": 1,
                                     "value": 100.5
                                   },
                                   {
                                     "date": "2024-01-01",
                                     "userId": 2,
                                     "deviceId": 2,
                                     "value": 200.3
                                   },
                                   {
                                     "date": "2024-01-01",
                                     "userId": 2,
                                     "deviceId": 3,
                                     "value": 150.7
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
            @ApiResponse(responseCode = "409", description = "Incorrect parameters",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject("""
                            {
                                "messages": [
                                  "Месяц должен быть задан в диапазоне от 1 до 12"
                                ]
                            }
                            """)))
    })
    List<MeterReadingResponseDto> findMeterReadingRecordsByMonth(@RequestAttribute("username") String username, @RequestParam("month") String monthString, @RequestParam("year") String yearString);

    @Operation(summary = "Find all relevant meter reading records", tags = "MeterReading",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relevant meter reading records retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeterReadingResponseDto.class), examples = @ExampleObject("""
                            [
                                 {
                                   "date": "2024-01-01",
                                   "userId": 2,
                                   "deviceId": 1,
                                   "value": 100.5
                                 },
                                 {
                                   "date": "2024-01-01",
                                   "userId": 2,
                                   "deviceId": 2,
                                   "value": 200.3
                                 },
                                 {
                                   "date": "2024-01-01",
                                   "userId": 2,
                                   "deviceId": 3,
                                   "value": 150.7
                                 },
                                 {
                                   "date": "2024-02-26",
                                   "userId": 11,
                                   "deviceId": 3,
                                   "value": 1533.13
                                 },
                                 {
                                   "date": "2024-02-26",
                                   "userId": 11,
                                   "deviceId": 2,
                                   "value": 1533.13
                                 },
                                 {
                                   "date": "2024-02-26",
                                   "userId": 11,
                                   "deviceId": 1,
                                   "value": 1533.13
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
                            """)))
    })
    List<MeterReadingResponseDto> findRelevantMeterReadingRecords(@RequestAttribute("username") String username);
}
