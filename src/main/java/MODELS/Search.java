package MODELS;

import java.util.List;

public interface Search {
    User searchUserByUsernameAndPassword(String username, String password);

    Location searchLocationById(int id);

    Location searchEventsByLocationName(String locationName);

    List<Event> searchEventsByDate(String date);

    List<Event> searchEventByCategory(EventCategory category);
}
