package by.harlap.monitoring.repository.impl;

import by.harlap.monitoring.model.User;
import by.harlap.monitoring.model.UserEvent;
import by.harlap.monitoring.repository.AuditRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The InMemoryAuditRepository class provides an in-memory implementation of the AuditRepository interface.
 * It stores user events in a map where each user is associated with a list of events.
 */
public class InMemoryAuditRepository implements AuditRepository {

    /**
     * The map to store user events, where each user is associated with a list of events.
     */
    private final Map<User, List<UserEvent>> events = new HashMap<>();

    /**
     * Saves a user event by adding it to the list of events associated with the user.
     *
     * @param event The user event to be saved.
     */
    @Override
    public void save(UserEvent event) {
        final User user = event.getUser();

        events.putIfAbsent(user, new ArrayList<>());
        events.get(user).add(event);
    }

    /**
     * Retrieves a list of all user events stored in the in-memory repository.
     * This method returns a flattened list of all user events from each user's associated list of events.
     *
     * @return A list of all user events in the repository.
     */
    @Override
    public List<UserEvent> findAll() {
        return events.values().stream()
                .flatMap(List::stream)
                .toList();
    }
}
