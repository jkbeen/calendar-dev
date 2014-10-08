package com.mycompany.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.mycompany.myapp.dao.CalendarUserDao;
import com.mycompany.myapp.dao.EventDao;
import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;
import com.mycompany.myapp.domain.EventAttendee;
import com.mycompany.myapp.domain.EventLevel;

@Service
public class DefaultCalendarService implements CalendarService {
	@Autowired
    private EventDao eventDao;
	
	@Autowired
    private CalendarUserDao userDao;

	@Autowired
	private PlatformTransactionManager transactionManager;
	

	/* CalendarUser */
	@Override
    public CalendarUser getUser(int id) {
		// TODO Assignment 3
		return null;
	}

	@Override
    public CalendarUser getUserByEmail(String email) {
		// TODO Assignment 3
		return null;
	}

	@Override
    public List<CalendarUser> getUsersByEmail(String partialEmail) {
		// TODO Assignment 3
		return null;
	}

	@Override
    public int createUser(CalendarUser user) {
		// TODO Assignment 3
		return -1;
	}
    
	@Override
    public void deleteAllUsers() {
		// TODO Assignment 3
	}
	
    
	
    /* Event */
	@Override
    public Event getEvent(int eventId) {
		// TODO Assignment 3
		return null;
	}

	@Override
    public List<Event> getEventForOwner(int ownerUserId) {
		// TODO Assignment 3
		return null;
	}

	@Override
    public List<Event> getAllEvents() {
		// TODO Assignment 3
		return null;
	}

	@Override
    public int createEvent(Event event) {
		// TODO Assignment 3
		
		if (event.getEventLevel() == null) {
			event.setEventLevel(EventLevel.NORMAL);
		}
		
		return -1;
	}
    
	@Override
    public void deleteAllEvents() {
		// TODO Assignment 3
	}

	
	
    /* EventAttendee */
	@Override
	public List<EventAttendee> getEventAttendeeByEventId(int eventId) {
		// TODO Assignment 3
		return null;
	}

	@Override
	public List<EventAttendee> getEventAttendeeByAttendeeId(int attendeeId) {
		// TODO Assignment 3
		return null;
	}

	@Override
	public int createEventAttendee(EventAttendee eventAttendee) {
		// TODO Assignment 3
		return 0;
	}

	@Override
	public void deleteEventAttendee(int id) {
		// TODO Assignment 3
	}

	@Override
	public void deleteAllEventAttendees() {
		// TODO Assignment 3
	}
	
	
	
	/* upgradeEventLevels */
	@Override
	public void upgradeEventLevels() throws Exception{
		// TODO Assignment 3
	}

	@Override
	public boolean canUpgradeEventLevel(Event event) {
		// TODO Assignment 3
		return false;
	}
	
	@Override
	public void upgradeEventLevel(Event event) {
		event.upgradeLevel();
		eventDao.udpateEvent(event);
	}
}