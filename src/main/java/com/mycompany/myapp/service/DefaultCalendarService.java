package com.mycompany.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.dao.CalendarUserDao;
import com.mycompany.myapp.dao.EventDao;
import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

@Service
public class DefaultCalendarService implements CalendarService {
	@Autowired
    private EventDao eventDao;
	
	@Autowired
    private CalendarUserDao userDao;

	/* CalendarUser */
	@Override
    public CalendarUser getUser(int id) {
		/* Assignment 3 */
		return null;
	}

	@Override
    public CalendarUser getUserByEmail(String email) {
		/* Assignment 3 */
		return null;
	}

	@Override
    public List<CalendarUser> getUsersByEmail(String partialEmail) {
		/* Assignment 3 */
		return null;
	}

	@Override
    public int createUser(CalendarUser user) {
		/* Assignment 3 */
		return -1;
	}
    
	@Override
    public void deleteAllUsers() {
		/* Assignment 3 */
	}
    
    /* Event */
	@Override
    public Event getEvent(int eventId) {
		/* Assignment 3 */
		return null;
	}

	@Override
    public List<Event> getEventForOwner(int ownerUserId) {
		/* Assignment 3 */
		return null;
	}

	@Override
    public List<Event> getAllEvents() {
		/* Assignment 3 */
		return null;
	}

	@Override
    public int createEvent(Event event) {
		/* Assignment 3 */
		return -1;
	}
    
	@Override
    public void deleteAllEvents() {
		/* Assignment 3 */
	}
}