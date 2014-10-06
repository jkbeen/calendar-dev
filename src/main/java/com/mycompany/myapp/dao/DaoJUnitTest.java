package com.mycompany.myapp.dao;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../applicationContext.xml")

public class DaoJUnitTest {
	@Autowired
	private CalendarUserDao calendarUserDao;	
	
	@Autowired
	private EventDao eventDao;
	
	private CalendarUser[] calendarUsers = null;
	private Event[] events = null;
	
	@Before
	public void setUp() {
		calendarUsers = new CalendarUser[3];
		events = new Event[3];
		
		this.calendarUserDao.deleteAll();
		this.eventDao.deleteAll();
		
		/* [참고]
		insert into calendar_users(`id`,`email`,`password`,`name`) values (1,'user1@example.com','user1','User1');
		insert into calendar_users(`id`,`email`,`password`,`name`) values (2,'admin1@example.com','admin1','Admin');
		insert into calendar_users(`id`,`email`,`password`,`name`) values (3,'user2@example.com','user2','User1');

		insert into events (`id`,`when`,`summary`,`description`,`owner`,`attendee`) values (100,'2013-10-04 20:30:00','Birthday Party','This is going to be a great birthday',1,2);
		insert into events (`id`,`when`,`summary`,`description`,`owner`,`attendee`) values (101,'2013-12-23 13:00:00','Conference Call','Call with the client',3,1);
		insert into events (`id`,`when`,`summary`,`description`,`owner`,`attendee`) values (102,'2014-01-23 11:30:00','Lunch','Eating lunch together',2,3);
		*/
		
		// 1. SQL 코드에 존재하는 3개의 CalendarUser와 Event 각각을 Fixture로서 인스턴스 변수 calendarUsers와 events에 등록하고 DB에도 저장한다. 
		// [주의 1] 모든 id 값은 입력하지 않고 DB에서 자동으로 생성되게 만든다. 
		// [주의 2] Calendar를 생성할 때에는 DB에서 자동으로 생성된 id 값을 받아 내어서 Events를 만들 때 owner와 attendee 값으로 활용한다.
		for(int i=0; i<3; i++)
			calendarUsers[i] = new CalendarUser();
		for(int i=0; i<3; i++)
			events[i] = new Event();
		
		calendarUsers[0].setEmail("user1@example.com");
		calendarUsers[0].setPassword("user1");
		calendarUsers[0].setName("User1");
		calendarUsers[0].setId(calendarUserDao.createUser(calendarUsers[0]));
		
		calendarUsers[1].setEmail("admin1@example.com");
		calendarUsers[1].setPassword("admin1");
		calendarUsers[1].setName("Admin");
		calendarUsers[1].setId(calendarUserDao.createUser(calendarUsers[1]));
		
		calendarUsers[2].setEmail("user2@example.com");
		calendarUsers[2].setPassword("user2");
		calendarUsers[2].setName("User1");
		calendarUsers[2].setId(calendarUserDao.createUser(calendarUsers[2]));
		
		Calendar when1 = Calendar.getInstance();
		when1.set(2013, 10, 4, 20, 30, 0);
		events[0].setWhen(when1);
		events[0].setSummary("Birthday Party");
		events[0].setDescription("This is going to be a great birthday");
		events[0].setOwner(calendarUsers[0]);
		events[0].setAttendee(calendarUsers[1]);
		events[0].setId(eventDao.createEvent(events[0]));
		
		Calendar when2 = Calendar.getInstance();
		when2.set(2013, 12, 23, 13, 0, 0);
		events[1].setWhen(when2);
		events[1].setSummary("Conference Call");
		events[1].setDescription("Call with the client");
		events[1].setOwner(calendarUsers[2]);
		events[1].setAttendee(calendarUsers[0]);
		events[1].setId(eventDao.createEvent(events[1]));
		
		Calendar when3 = Calendar.getInstance();
		when3.set(2014, 1, 23, 11, 30, 0);
		events[2].setWhen(when3);
		events[2].setSummary("Lunch");
		events[2].setDescription("Eating lunch together");
		events[2].setOwner(calendarUsers[1]);
		events[2].setAttendee(calendarUsers[2]);
		events[2].setId(eventDao.createEvent(events[2]));
	}
	
	@Test
	public void createCalendarUserAndCompare() {
		// 2. 새로운 CalendarUser 2명 등록 및 각각 id 추출하고, 추출된 id와 함께 새로운 CalendarUser 2명을 DB에서 가져와 (getUser 메소드 사용) 
		//    방금 등록된 2명의 사용자와 내용 (이메일, 이름, 패스워드)이 일치하는 지 비교
		int user1Id, user2Id;
		
		CalendarUser createUser1 = new CalendarUser();
		createUser1.setEmail("cUser1@example.com");
		createUser1.setPassword("user1");
		createUser1.setName("createUser1");
		user1Id = calendarUserDao.createUser(createUser1);
		
		CalendarUser createUser2 = new CalendarUser();
		createUser2.setEmail("cUser2@example.com");
		createUser2.setPassword("user2");
		createUser2.setName("createUser2");
		user2Id = calendarUserDao.createUser(createUser2);
		
		CalendarUser getUser1 = calendarUserDao.getUser(user1Id);
		CalendarUser getUser2 = calendarUserDao.getUser(user2Id);
		
		assertThat(createUser1.getEmail(), is(getUser1.getEmail()));
		assertThat(createUser1.getPassword(), is(getUser1.getPassword()));
		assertThat(createUser1.getName(), is(getUser1.getName()));
		
		assertThat(createUser2.getEmail(), is(getUser2.getEmail()));
		assertThat(createUser2.getPassword(), is(getUser2.getPassword()));
		assertThat(createUser2.getName(), is(getUser2.getName()));
	}
	
	@Test
	public void createEventUserAndCompare() {
		// 3. 새로운 Event 2개 등록 및 각각 id 추출하고, 추출된 id와 함께 새로운 Event 2개를 DB에서 가져와 (getEvent 메소드 사용) 
		//    방금 추가한 2개의 이벤트와 내용 (summary, description, owner, attendee)이 일치하는 지 비교
		// [주의 1] when은 비교하지 않아도 좋다.
		// [주의 2] owner와 attendee는 @Before에서 미리 등록해 놓은 3명의 CalendarUser 중에서 임의의 것을 골라 활용한다.
		int eventId1, eventId2;
		
		Event createEvent1 = new Event();
		createEvent1.setWhen(Calendar.getInstance());
		createEvent1.setSummary("Event 1 Summary");
		createEvent1.setDescription("Event 1 Description");
		createEvent1.setOwner(calendarUsers[0]);
		createEvent1.setAttendee(calendarUsers[1]);
		eventId1 = eventDao.createEvent(createEvent1);
		
		Event createEvent2 = new Event();
		createEvent2.setWhen(Calendar.getInstance());
		createEvent2.setSummary("Event 2 Summary");
		createEvent2.setDescription("Event 2 Description");
		createEvent2.setOwner(calendarUsers[1]);
		createEvent2.setAttendee(calendarUsers[2]);
		eventId2 = eventDao.createEvent(createEvent2);
		
		Event getEvent1 = eventDao.getEvent(eventId1);
		Event getEvent2 = eventDao.getEvent(eventId2);
		
		assertThat(createEvent1.getSummary(), is(getEvent1.getSummary()));
		assertThat(createEvent1.getDescription(), is(getEvent1.getDescription()));
		assertThat(createEvent1.getOwner(), is(getEvent1.getOwner()));
		assertThat(createEvent1.getAttendee(), is(getEvent1.getAttendee()));
		
		assertThat(createEvent2.getSummary(), is(getEvent2.getSummary()));
		assertThat(createEvent2.getDescription(), is(getEvent2.getDescription()));
		assertThat(createEvent2.getOwner(), is(getEvent2.getOwner()));
		assertThat(createEvent2.getAttendee(), is(getEvent2.getAttendee()));
	}
	
	@Test
	public void getAllEvent() {
		// 4. 모든 Events를 가져오는 eventDao.getEvents()가 올바로 동작하는 지 (총 3개를 가지고 오는지) 확인하는 테스트 코드 작성  
		// [주의] fixture로 등록된 3개의 이벤트들에 대한 테스트
		assertThat(eventDao.getEvents().size(), is(3));
	}
	
	@Test
	public void getEvent() {
		// 5. owner ID가 3인 Event에 대해 findForOwner가 올바로 동작하는 지 확인하는 테스트 코드 작성  
		// [주의] fixture로 등록된 3개의 이벤트들에 대해서 owner ID가 3인 것인 1개의 이벤트뿐임
		assertThat(eventDao.findForOwner(calendarUsers[2].getId()).size(), is(1));
	}
	
	@Test
	public void getOneUserByEmail() {
		// 6. email이 'user1@example.com'인 CalendarUser가 존재함을 확인하는 테스트 코드 작성 (null인지 아닌지)
		// [주의] public CalendarUser findUserByEmail(String email)를 테스트 하는 코드
		assertTrue(calendarUserDao.findUserByEmail("user1@example.com") != null);
	}
	
	@Test
	public void getTwoUserByEmail() {
		// 7. partialEmail이 'user'인 CalendarUser가 2명임을 확인하는 테스크 코드 작성
		// [주의] public List<CalendarUser> findUsersByEmail(String partialEmail)를 테스트 하는 코드
		assertThat(calendarUserDao.findUsersByEmail("user").size(), is(2));
	}
}
