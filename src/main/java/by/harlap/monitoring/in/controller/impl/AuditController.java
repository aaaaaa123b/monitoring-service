package by.harlap.monitoring.in.controller.impl;

import by.harlap.monitoring.dto.userEvent.UserEventDto;
import by.harlap.monitoring.enumeration.Role;
import by.harlap.monitoring.initialization.DependencyFactory;
import by.harlap.monitoring.mapper.UserEventMapper;
import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.AuditService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The AuditController class extends AbstractController and is responsible for displaying user audit events.
 */
@WebServlet("/audit")
public class AuditController extends AbstractController {

    private AuditService auditService;
    private UserEventMapper userEventMapper;

    /**
     * Initializes the controller by initializing necessary dependencies.
     */
    @Override
    public void init() {
        super.init();

        userEventMapper = DependencyFactory.findMapper(UserEventMapper.class);
        auditService = DependencyFactory.findService(AuditService.class);
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
        final User activeUser = findActiveUser(request);

        validateRequiredRole(activeUser, Role.ADMIN);

        final List<UserEventDto> responseData = createEventResponse();

        write(response, responseData, HttpServletResponse.SC_OK);
    }

    private List<UserEventDto> createEventResponse(){
        List<UserEventDto> eventResponseDtos = new ArrayList<>();
        for (UserEvent event : auditService.findAllUserEvents()) {

            UserEventDto eventResponseDto = userEventMapper.toDto(event);

            eventResponseDtos.add(eventResponseDto);
        }
        return eventResponseDtos;
    }
}
