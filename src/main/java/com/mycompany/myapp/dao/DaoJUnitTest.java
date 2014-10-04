package com.mycompany.myapp.dao;

import java.sql.SQLException;
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
	
	private String inputEmail;
	
	int i;
	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		calendarUsers = new CalendarUser[3];
		events = new Event[3];
		
		this.calendarUserDao.deleteAll();
		this.eventDao.deleteAll();
		
		CalendarUser tempUser1 = new CalendarUser();
		CalendarUser tempUser2 = new CalendarUser();
		CalendarUser tempUser3 = new CalendarUser();
		
		tempUser1.setEmail("user1@example.com");
		tempUser1.setPassword("user1");
		tempUser1.setName("User1");
		calendarUsers[0] = tempUser1;
		
		tempUser2.setEmail("admin1@example.com");
		tempUser2.setPassword("admin1");
		tempUser2.setName("Admin1");
		calendarUsers[1] = tempUser2;
	
		tempUser3.setEmail("user2@example.com");
		tempUser3.setPassword("user2");
		tempUser3.setName("User2");
		calendarUsers[2] = tempUser3;
		
		for(i=0;i<3;i++){
			calendarUserDao.createUser(calendarUsers[i]);
		}
		
		
		Event tempEvent1 = new Event();
		Event tempEvent2 = new Event();
		Event tempEvent3 = new Event();
		

		tempEvent1.setWhen(Calendar.getInstance());
		tempEvent1.setSummary("Birthday Party");
		tempEvent1.setDescription("This is going to be a great birthday");
		tempEvent1.setOwner(calendarUserDao.getUser(calendarUserDao.findUserByEmail("user1@example.com").getId()));
		tempEvent1.setAttendee(calendarUserDao.getUser(calendarUserDao.findUserByEmail("admin1@example.com").getId()));
		events[0] = tempEvent1;
		
		tempEvent2.setWhen(Calendar.getInstance());
		tempEvent2.setSummary("Conference Call");
		tempEvent2.setDescription("Call with the client");
		tempEvent2.setOwner(calendarUserDao.getUser(calendarUserDao.findUserByEmail("user2@example.com").getId()));
		tempEvent2.setAttendee(calendarUserDao.getUser(calendarUserDao.findUserByEmail("user1@example.com").getId()));
		events[1] = tempEvent2;
		
		tempEvent3.setWhen(Calendar.getInstance());
		tempEvent3.setSummary("Lunch");
		tempEvent3.setDescription("Eating lunch together");
		tempEvent3.setOwner(calendarUserDao.getUser(calendarUserDao.findUserByEmail("admin1@example.com").getId()));
		tempEvent3.setAttendee(calendarUserDao.getUser(calendarUserDao.findUserByEmail("user1@example.com").getId()));
		events[2] = tempEvent3;
		
		for(i=0;i<3;i++){
			//calendarUserDao.createUser(calendarUsers[i]);
			eventDao.createEvent(events[i]);
		}
	
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
	}
	
	@Test
	public void createCalendarUserAndCompare() throws ClassNotFoundException, SQLException {
		// 2. 새로운 CalendarUser 2명 등록 및 각각 id 추출하고, 추출된 id와 함께 새로운 CalendarUser 2명을 DB에서 가져와 (getUser 메소드 사용) 
		//    방금 등록된 2명의 사용자와 내용 (이메일, 이름, 패스워드)이 일치하는 지 비교
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
		
	}
	
	@Test
	public void createEventUserAndCompare() throws ClassNotFoundException, SQLException {
		// 3. 새로운 Event 2개 등록 및 각각 id 추출하고, 추출된 id와 함께 새로운 Event 2개를 DB에서 가져와 (getEvent 메소드 사용) 
		//    방금 추가한 2개의 이벤트와 내용 (summary, description, owner, attendee)이 일치하는 지 비교
		// [주의 1] when은 비교하지 않아도 좋다.
		// [주의 2] owner와 attendee는 @Before에서 미리 등록해 놓은 3명의 CalendarUser 중에서 임의의 것을 골라 활용한다.
		
		Event createEvent1 = new Event();
		Event createEvent2 = new Event();
		int createEvent1Id;
		int createEvent2Id;
		
		createEvent1.setWhen(Calendar.getInstance());
		createEvent1.setSummary("event1 - summary");
		createEvent1.setDescription("event1 - description");
		createEvent1.setOwner(calendarUserDao.getUser(calendarUserDao.findUserByEmail("user1@example.com").getId()));
		createEvent1.setAttendee(calendarUserDao.getUser(calendarUserDao.findUserByEmail("user2@example.com").getId()));
		
		createEvent2.setWhen(Calendar.getInstance());
		createEvent2.setSummary("event2 - summary");
		createEvent2.setDescription("event2 - description");
		createEvent2.setOwner(calendarUserDao.getUser(calendarUserDao.findUserByEmail("admin1@example.com").getId()));
		createEvent2.setAttendee(calendarUserDao.getUser(calendarUserDao.findUserByEmail("user1@example.com").getId()));
		
		createEvent1Id = eventDao.createEvent(createEvent1);
		createEvent2Id = eventDao.createEvent(createEvent2);
		
		System.out.println("createEvent1 Id: "+createEvent1Id);
		System.out.println("createEvent2 Id: "+createEvent2Id);
		
		
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
		
	}
	
	@Test
	public void getAllEvent() throws ClassNotFoundException {
		// 4. 모든 Events를 가져오는 eventDao.getEvents()가 올바로 동작하는 지 (총 3개를 가지고 오는지) 확인하는 테스트 코드 작성  
		// [주의] fixture로 등록된 3개의 이벤트들에 대한 테스트
		
		int count = 0;
		List<Event> events = eventDao.getEvents();
		for(Event event: events)
		{
			if(event.getSummary().equals("Birthday Party"))
				count++;
			else if(event.getSummary().equals("Conference Call"))
				count++;
			else if(event.getSummary().equals("Lunch"))
				count++;
		}
		if(count == events.size())
			System.out.println("3개의 이벤트가 성공적으로 등록되었습니다.");
		 
	}
	
	@Test
	public void getEvent() throws ClassNotFoundException {
		// 5. owner ID가 3인 Event에 대해 findForOwner가 올바로 동작하는 지 확인하는 테스트 코드 작성  
		// [주의] fixture로 등록된 3개의 이벤트들에 대해서 owner ID가 3인 것인 1개의 이벤트뿐임 
		
		int ID = 3;
		List<Event> events = eventDao.findForOwner(ID);
		
		if(events.size() == 1)
			System.out.println("onwer ID : 1개, TEST 성공!");
		else
			System.out.println("실패");
		
	}
	
	@Test
	public void getOneUserByEmail() {
		// 6. email이 'user1@example.com'인 CalendarUser가 존재함을 확인하는 테스트 코드 작성 (null인지 아닌지)
		// [주의] public CalendarUser findUserByEmail(String email)를 테스트 하는 코드
		
		
		inputEmail = "user1@example.com";
		CalendarUser testUser = calendarUserDao.findUserByEmail(inputEmail);
		
		if(testUser==null){
			System.out.println("상태 : null, test에 실패했습니다.");
		}
		else
			System.out.println(testUser.getEmail()+", test 성공");
		 
	}
	
	@Test
	public void getTwoUserByEmail() {
		// 7. partialEmail이 'user'인 CalendarUser가 2명임을 확인하는 테스크 코드 작성
		// [주의] public List<CalendarUser> findUsersByEmail(String partialEmail)를 테스트 하는 코드
		inputEmail = "user";
		List<CalendarUser> testUsers = calendarUserDao.findUsersByEmail(inputEmail);
		assertThat(testUsers.size(), is(2));
		/*
		if(testUsers.size()==2)
			System.out.println("검색된 이메일 : 2개, test 성공");
		else
			System.out.println("test에 실패했습니다.");
		*/
	}
}
