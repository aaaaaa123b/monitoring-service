package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.AuditFacade;
import by.harlap.monitoring.in.controller.openapi.AuditOpenAPI;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.starter.annotations.Loggable;
import by.harlap.monitoring.util.SecurityUtil;
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
public class AuditController implements AuditOpenAPI {

    private final AuditFacade auditFacade;
    private final SecurityUtil securityUtil;

    /**
     * Retrieves user audit events and returns them as a list of UserEventResponseDto.
     * This endpoint requires an authenticated user with the role of ADMIN.
     *
     * @param username the username obtained from the request attribute
     * @return ResponseEntity containing a list of UserEventResponseDto
     */
    @GetMapping
    public List<UserEventResponseDto> findAllAudit(@RequestAttribute("username") String username) {
        final User activeUser = securityUtil.findActiveUser(username);
        securityUtil.validateRequiredRole(activeUser, Role.ADMIN);

        return auditFacade.findUserEvents();
    }
}
