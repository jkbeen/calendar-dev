package com.mycompany.myapp.dao;


import java.util.List;

import com.mycompany.myapp.domain.Event;

public interface EventDao {
	
    public Event getEvent(int eventId) throws ClassNotFoundException;

    public int createEvent(Event event);

    public List<Event> findForOwner(int ownerUserId) throws ClassNotFoundException;

    public List<Event> getEvents() throws ClassNotFoundException;
    
    public void deleteAll();
}