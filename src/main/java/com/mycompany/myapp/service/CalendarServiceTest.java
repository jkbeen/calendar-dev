package com.mycompany.myapp.service;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../applicationContext.xml")

public class CalendarServiceTest {
	@Autowired
	private CalendarService calendarService;	

	private CalendarUser[] calendarUsers = null;
	private Event[] events = null;

	@Before
	public void setUp() {
		calendarUsers = new CalendarUser[3];
		events = new Event[3];
		
		this.calendarService.deleteAllUsers();
		this.calendarService.deleteAllEvents();
		
		for(int i=0; i<3; i++)
			calendarUsers[i] = new CalendarUser();
		for(int i=0; i<3; i++)
			events[i] = new Event();
		
		calendarUsers[0].setEmail("user1@example.com");
		calendarUsers[0].setPassword("user1");
		calendarUsers[0].setName("User1");
		calendarUsers[0].setId(calendarService.createUser(calendarUsers[0]));
		
		calendarUsers[1].setEmail("admin1@example.com");
		calendarUsers[1].setPassword("admin1");
		calendarUsers[1].setName("Admin");
		calendarUsers[1].setId(calendarService.createUser(calendarUsers[1]));
		
		calendarUsers[2].setEmail("user2@example.com");
		calendarUsers[2].setPassword("user2");
		calendarUsers[2].setName("User1");
		calendarUsers[2].setId(calendarService.createUser(calendarUsers[2]));
		
		Calendar when1 = Calendar.getInstance();
		when1.set(2013, 10, 4, 20, 30, 0);
		events[0].setWhen(when1);
		events[0].setSummary("Birthday Party");
		events[0].setDescription("This is going to be a great birthday");
		events[0].setOwner(calendarUsers[0]);
		events[0].setAttendee(calendarUsers[1]);
		events[0].setId(calendarService.createEvent(events[0]));
		
		Calendar when2 = Calendar.getInstance();
		when2.set(2013, 12, 23, 13, 0, 0);
		events[1].setWhen(when2);
		events[1].setSummary("Conference Call");
		events[1].setDescription("Call with the client");
		events[1].setOwner(calendarUsers[2]);
		events[1].setAttendee(calendarUsers[0]);
		events[1].setId(calendarService.createEvent(events[1]));
		
		Calendar when3 = Calendar.getInstance();
		when3.set(2014, 1, 23, 11, 30, 0);
		events[2].setWhen(when3);
		events[2].setSummary("Lunch");
		events[2].setDescription("Eating lunch together");
		events[2].setOwner(calendarUsers[1]);
		events[2].setAttendee(calendarUsers[2]);
		events[2].setId(calendarService.createEvent(events[2]));
	}
	
	@Test
	public void CalendarServiceBeanTest() {
		assertThat(calendarService, notNullValue() );
	}
}