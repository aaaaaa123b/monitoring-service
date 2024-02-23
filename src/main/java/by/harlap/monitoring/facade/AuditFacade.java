package by.harlap.monitoring.facade;

import by.harlap.monitoring.dto.userEvent.UserEventResponseDto;
import by.harlap.monitoring.mapper.UserEventMapper;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The AuditFacade class provides a facade for accessing user event audit data.
 */
@RequiredArgsConstructor
@Component
public class AuditFacade {

    private final UserEventMapper userEventMapper;
    private final AuditService auditService;

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
