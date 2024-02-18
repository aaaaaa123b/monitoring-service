package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.mapper.UserEventMapper;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.AuditService;

import java.util.ArrayList;
import java.util.List;

/**
 * The AuditFacade class provides a facade for accessing user event audit data.
 */
public class AuditFacade {

    private final UserEventMapper userEventMapper;
    private final AuditService auditService;

    /**
     * Constructs an AuditFacade with the specified UserEventMapper and AuditService.
     *
     * @param userEventMapper the mapper used to map UserEvent objects to UserEventResponseDto objects
     * @param auditService    the service used to retrieve user events
     */
    public AuditFacade(UserEventMapper userEventMapper, AuditService auditService) {
        this.userEventMapper = userEventMapper;
        this.auditService = auditService;
    }

    /**
     * Retrieves a list of user events and maps them to UserEventResponseDto objects.
     *
     * @return a list of UserEventResponseDto objects representing user events
     */
    public List<UserEventResponseDto> findUserEvents() {
        final List<UserEventResponseDto> eventResponseDtoList = new ArrayList<>();

        for (final UserEvent event : auditService.findAllUserEvents()) {
            UserEventResponseDto eventResponseDto = userEventMapper.toDto(event);
            eventResponseDtoList.add(eventResponseDto);
        }

        return eventResponseDtoList;
    }
}
