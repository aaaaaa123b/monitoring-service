package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.facade.AuditFacade;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.util.IOUtil;
import by.harlap.monitoring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * The AuditController class extends BaseController and is responsible for displaying user audit events.
 */
public class AuditController extends BaseController {

    private final AuditFacade auditFacade;

    /**
     * Constructs an AuditController object with the specified AuditFacade.
     *
     * @param auditFacade the AuditFacade object to use for retrieving user audit events
     */
    public AuditController(AuditFacade auditFacade) {
        this.auditFacade = auditFacade;
    }

    /**
     * Handles HTTP GET requests for retrieving user audit events.
     * Requires the user to have admin role.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final User activeUser = SecurityUtil.findActiveUser(request);
        SecurityUtil.validateRequiredRole(activeUser, Role.ADMIN);

        final List<UserEventResponseDto> responseData = auditFacade.findUserEvents();

        IOUtil.write(response, responseData, HttpServletResponse.SC_OK);
    }
}
