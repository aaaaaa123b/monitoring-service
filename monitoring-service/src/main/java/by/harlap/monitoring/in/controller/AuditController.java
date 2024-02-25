package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.AuditFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.starter.annotations.Loggable;
import by.harlap.monitoring.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The AuditController class extends BaseController and is responsible for displaying user audit events.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/audit")
@Loggable
@Tag(name = "Audit", description = "Find all audit")
public class AuditController {

    private final AuditFacade auditFacade;
    private final SecurityUtil securityUtil;

    /**
     * Retrieves user audit events and returns them as a list of UserEventResponseDto.
     * This endpoint requires an authenticated user with the role of ADMIN.
     *
     * @param username the username obtained from the request attribute
     * @return ResponseEntity containing a list of UserEventResponseDto
     */
    @Operation(summary = "Find all audit events", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public List<UserEventResponseDto> findAllAudit(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);
        securityUtil.validateRequiredRole(activeUser, Role.ADMIN);

        return auditFacade.findUserEvents();
    }
}
