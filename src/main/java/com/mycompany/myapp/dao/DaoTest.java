package com.mycompany.myapp.dao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.springframework.context.support.GenericXmlApplicationContext;


import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

public class DaoTest { 
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		ApplicationContext context = new GenericXmlApplicationContext("com/mycompany/myapp/applicationContext.xml");
		
		
		CalendarUserDao calendarUserDao = context.getBean("calendarUserDao", JdbcCalendarUserDao.class);
		EventDao eventDao = context.getBean("eventDao", JdbcEventDao.class);
		
		int defaultUserNum = 3;
		int i;
		
		//1. 디폴트로 등록된 CalendarUser 3명 출력 (패스워드 제외한 모든 내용 출력)
		System.out.println("1. 디폴트로 등록된 CalendarUser 3명 출력 (패스워드 제외한 모든 내용 출력)");
		
		for(i=1;i<=defaultUserNum;i++){
			CalendarUser user = calendarUserDao.getUser(i);
			System.out.println("id : "+user.getId()+", email : "+user.getEmail()+", name : "+user.getName());
			System.out.println("****************");
		}
	
		//2. 디폴트로 등록된 Event 3개 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력) 
		System.out.println("2. 디폴트로 등록된 Event 3개 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력)");
		List<Event> events = eventDao.getEvents();
		
		for(Event event: events){
			
			System.out.println("id: " +event.getId()+"\nwhen: "+ event.getWhen().getTime() +"\nsummary :" +event.getSummary()+"\ndescription :"+event.getDescription());
			System.out.println("owner\n- email: " +event.getOwner().getEmail()+"\tname: "+event.getOwner().getName());
			System.out.println("attendee\n- email: " +event.getAttendee().getEmail()+"\tname: "+event.getAttendee().getName()+"\n");
		}
		
		
		//3. 새로운 CalendarUser 2명 등록 및 각각 id 추출
		System.out.println("3. 새로운 CalendarUser 2명 등록 및 각각 id 추출");
		CalendarUser newUser1 = new CalendarUser();
		CalendarUser newUser2 = new CalendarUser();
		int newUser1Id;
		int newUser2Id;
		
		
		newUser1.setEmail("gilson@gilson.com");
		newUser1.setPassword("gilson");
		newUser1.setName("gilson");
		newUser2.setEmail("been@been.com");
		newUser2.setPassword("been");
		newUser2.setName("been");
		System.out.println("****************");
		
		newUser1Id = calendarUserDao.createUser(newUser1);
		newUser2Id = calendarUserDao.createUser(newUser2);
		
		System.out.println("createUser1 Id: "+newUser1Id);
		System.out.println("createUser2 Id: "+newUser2Id);
		System.out.println("****************");
		
		//CalendarUser getNewUser2 = calendarUserDao.getUser(newUser2.getId());
		
		//4. 추출된 id와 함께 새로운 CalendarUser 2명을 DB에서 가져와 (getUser 메소드 사용) 방금 등록된 2명의 사용자와 내용 (이메일, 이름, 패스워드)이 일치하는 지 비교
		System.out.println("4. 추출된 id와 함께 새로운 CalendarUser 2명을 DB에서 가져와 (getUser 메소드 사용) 방금 등록된 2명의 사용자와 내용 (이메일, 이름, 패스워드)이 일치하는 지 비교");
		
		CalendarUser getNewUser1 = calendarUserDao.getUser(newUser1Id);
		CalendarUser getNewUser2 = calendarUserDao.getUser(newUser2Id);
		
		if(newUser1.getEmail().equals(getNewUser1.getEmail()) && newUser1.getName().equals(getNewUser1.getName()) && newUser1.getPassword().equals(getNewUser1.getPassword()))
			System.out.println("newUser1 일치");
		else
			System.out.println("newUser1 불일치");
		if(newUser2.getEmail().equals(getNewUser2.getEmail()) && newUser1.getName().equals(getNewUser2.getName()) && newUser1.getPassword().equals(getNewUser2.getPassword()))
			System.out.println("newUser2 일치");
		else
			System.out.println("newUser2 불일치");
		
		
		
		//5. 5명의 CalendarUser 모두 출력 (패스워드 제외한 모든 내용 출력)
		System.out.println("5. 5명의 CalendarUser 모두 출력 (패스워드 제외한 모든 내용 출력)");
		List<CalendarUser> calendarUsers = calendarUserDao.findUsersByEmail(null);
		//System.out.println(userList.size());

		for(CalendarUser calendarUser: calendarUsers)
		{
			System.out.println("id: "+calendarUser.getId() + "\temail: " + calendarUser.getEmail() + "\tname: " + calendarUser.getName());			
		}
		
		//6. 새로운 Event 2개 등록 및 각각 id 추출
		System.out.println("\n6. ---------------------------------------------------------");
		Event createEvent1 = new Event();
		Event createEvent2 = new Event();
		int createEvent1Id;
		int createEvent2Id;
		
		createEvent1.setWhen(Calendar.getInstance());
		createEvent1.setSummary("event1 - summary");
		createEvent1.setDescription("event1 - description");
		createEvent1.setOwner(calendarUserDao.getUser(1));
		createEvent1.setAttendee(calendarUserDao.getUser(2));
		
		createEvent2.setWhen(Calendar.getInstance());
		createEvent2.setSummary("event2 - summary");
		createEvent2.setDescription("event2 - description");
		createEvent2.setOwner(calendarUserDao.getUser(3));
		createEvent2.setAttendee(calendarUserDao.getUser(1));
		
		createEvent1Id = eventDao.createEvent(createEvent1);
		createEvent2Id = eventDao.createEvent(createEvent2);
		
		System.out.println("createEvent1 Id: "+createEvent1Id);
		System.out.println("createEvent2 Id: "+createEvent2Id);
		
		//7. 추출된 id와 함께 새로운 Event 2개를 DB에서 가져와 (getEvent 메소드 사용) 방금 추가한 2개의 이벤트와 내용 (when, summary, description, owner, attendee)이 일치하는 지 비교
		System.out.println("\n7. ---------------------------------------------------------");
		Event getCreateEvent1 = eventDao.getEvent(createEvent1Id);
		Event getCreateEvent2 = eventDao.getEvent(createEvent2Id);
		
		if(createEvent1.getWhen().getTime().compareTo(getCreateEvent1.getWhen().getTime())==0)
			System.out.println("equls비교");
		
		System.out.println(createEvent1.getWhen().getTime());
		System.out.println(getCreateEvent1.getWhen().getTime());
		
		if(createEvent1.getWhen().getTime().compareTo(getCreateEvent1.getWhen().getTime())==0 && createEvent1.getSummary().equals(getCreateEvent1.getSummary()) && createEvent1.getDescription().equals(getCreateEvent1.getDescription()) && createEvent1.getOwner().equals(getCreateEvent1.getOwner()) && createEvent1.getAttendee().equals(getCreateEvent1.getAttendee()))
			System.out.println("createEvent1 일치");
		else
			System.out.println("createEvent1 불일치");
		if(createEvent2.getWhen().getTime().compareTo(getCreateEvent2.getWhen().getTime())==0 && createEvent2.getSummary().equals(getCreateEvent2.getSummary()) && createEvent2.getDescription().equals(getCreateEvent2.getDescription()) && createEvent2.getOwner().equals(getCreateEvent2.getOwner()) && createEvent2.getAttendee().equals(getCreateEvent2.getAttendee()))
			System.out.println("createEvent2 일치");
		else
			System.out.println("createEvent2 불일치");
		
		//8. 5개의 Event 모두 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력)
		System.out.println("\n8. ---------------------------------------------------------");
		List<Event> events8 = eventDao.getEvents();
		for(Event event: events8)
		{
			System.out.println("id: " +event.getId()+"\nwhen: "+ event.getWhen().getTime() +"\nsummary :" +event.getSummary()+"\ndescription :"+event.getDescription());
			System.out.println("owner\n- email: " +event.getOwner().getEmail()+"\tname: "+event.getOwner().getName());
			System.out.println("attendee\n- email: " +event.getAttendee().getEmail()+"\tname: "+event.getAttendee().getName()+"\n");
		}


	}
}
