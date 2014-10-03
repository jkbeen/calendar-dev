package com.mycompany.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.mycompany.myapp.domain.Event;

public interface EventDao {

<<<<<<< HEAD
    Event getEvent(int eventId) throws SQLException;

    int createEvent(Event event) throws SQLException;
=======
    public Event getEvent(int eventId);

    public int createEvent(Event event);
>>>>>>> ce2d7ae748ad952391b5793a9d10854e9028d8ab

    public List<Event> findForOwner(int ownerUserId);

    public List<Event> getEvents();
    
    public void deleteAll();
}