package com.mycompany.myapp.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
		//JdbcCalendarUserDao jdbcCalendarUserDao = context.getBean("jdbcCalendarUserDao", JdbcCalendarUserDao.class);
		CalendarUserDao calendarUserDao = context.getBean("jdbcCalendarUserDao", JdbcCalendarUserDao.class);
		EventDao eventDao = context.getBean("jdbcEventDao", JdbcEventDao.class);
		
		int defaultUserNum = 3;
		int i;
		
		//1. 디폴트로 등록된 CalendarUser 3명 출력 (패스워드 제외한 모든 내용 출력)
		System.out.println("1. 디폴트로 등록된 CalendarUser 3명 출력 (패스워드 제외한 모든 내용 출력)");
		for(i=1;i<=defaultUserNum;i++){
			CalendarUser user = calendarUserDao.getUser(i);
			System.out.println(user.getId());
			System.out.println(user.getEmail());
			System.out.println(user.getName());
			System.out.println("****************");
		}
	
		//2. 디폴트로 등록된 Event 3개 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력) 
		System.out.println("2. 디폴트로 등록된 Event 3개 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력)");
		for(i=100;i<100+defaultUserNum;i++){
			Event event = eventDao.getEvent(i);
			System.out.println(event.getId());
			//System.out.println(event.getWhen());
			System.out.println(event.getSummary());
			System.out.println(event.getDescription());
			//System.out.println(event.getOwner());
			//System.out.println(event.getAttendee());
			System.out.println("****************");
		}
		
		
		//3. 새로운 CalendarUser 2명 등록 및 각각 id 추출
		System.out.println("3. 새로운 CalendarUser 2명 등록 및 각각 id 추출");
		CalendarUser newUser1 = new CalendarUser();
		newUser1.setEmail("gilson@gilson.com");
		newUser1.setPassword("gilson");
		newUser1.setName("gilson");
		System.out.println("****************");
		
		calendarUserDao.createUser(newUser1);
		
		CalendarUser getNewUser1 = new CalendarUser();
		getNewUser1 = calendarUserDao.findUserByEmail(newUser1.getEmail());
		System.out.println(getNewUser1.getId());
		
		//CalendarUser getNewUser1 = calendarUserDao.getUser(newUser1.getId());
		
		CalendarUser newUser2 = new CalendarUser();
		newUser2.setEmail("been@been.com");
		newUser2.setPassword("been");
		newUser2.setName("been");
		System.out.println("****************");
		
		calendarUserDao.createUser(newUser2);
		
		CalendarUser getNewUser2 = new CalendarUser();
		getNewUser2 = calendarUserDao.findUserByEmail(newUser2.getEmail());
		System.out.println(getNewUser2.getId());
		
		//CalendarUser getNewUser2 = calendarUserDao.getUser(newUser2.getId());
		
		//4. 추출된 id와 함께 새로운 CalendarUser 2명을 DB에서 가져와 (getUser 메소드 사용) 방금 등록된 2명의 사용자와 내용 (이메일, 이름, 패스워드)이 일치하는 지 비교
		System.out.println("//4. 추출된 id와 함께 새로운 CalendarUser 2명을 DB에서 가져와 (getUser 메소드 사용) 방금 등록된 2명의 사용자와 내용 (이메일, 이름, 패스워드)이 일치하는 지 비교");
		System.out.println("newUser1.getEmail()"+newUser1.getEmail());
		System.out.println("getNewUser1.getEmail()"+getNewUser1.getEmail());
		System.out.println("newUser1.getName()"+newUser1.getName());
		System.out.println("getNewUser1.getName()"+getNewUser1.getName());
		System.out.println("newUser1.getPassword()"+newUser1.getPassword());
		System.out.println("getNewUser1.getPassword()"+getNewUser1.getPassword());
		if(newUser1.getEmail().equals(getNewUser1.getEmail())){
			System.out.println("newUser1와 getNewUser1의 email이 일치합니다.");
		}
		else{
			System.out.println("newUser1와 getNewUser1의 email이 일치하지 않습니다.");
		}
		if(newUser1.getName().equals(getNewUser1.getName())){
			System.out.println("newUser1와 getNewUser1의 name이 일치합니다.");
		}
		else{
			System.out.println("newUser1와 getNewUser1의 name이 일치하지 않습니다.");
		}
		if(newUser1.getPassword().equals(getNewUser1.getPassword())){
			System.out.println("newUser1와 getNewUser1의 password이 일치합니다.");
		}
		else{
			System.out.println("newUser1와 getNewUser1의 password이 일치하지 않습니다.");
		}
		System.out.println("****************");
		System.out.println("newUser2.getEmail() : "+newUser2.getEmail());
		System.out.println("getNewUser2.getEmail() : "+getNewUser2.getEmail());
		System.out.println("newUser2.getName()"+newUser2.getName());
		System.out.println("getNewUser2.getName()"+getNewUser2.getName());
		System.out.println("newUser2.getPassword()"+newUser2.getPassword());
		System.out.println("getNewUser2.getPassword()"+getNewUser2.getPassword());
		
		if(newUser2.getEmail().equals(getNewUser2.getEmail())){
			System.out.println("newUser2와 getNewUser2의 email이 일치합니다.");
		}
		else{
			System.out.println("newUser2와 getNewUser2의 email이 일치합니다.");
		}
		if(newUser2.getName().equals(getNewUser2.getName())){
			System.out.println("newUser2와 getNewUser2의 name이 일치합니다.");
		}
		else{
			System.out.println("newUser2와 getNewUser2의 name이 일치하지 않습니다.");
		}
		if(newUser2.getPassword().equals(getNewUser2.getPassword())){
			System.out.println("newUser2와 getNewUser2의 password이 일치합니다.");
		}
		else{
			System.out.println("newUser2와 getNewUser2의 password이 일치하지 않습니다.");
		}
		//5. 5명의 CalendarUser 모두 출력 (패스워드 제외한 모든 내용 출력)
		List<CalendarUser> userList = calendarUserDao.getUserList();
		//System.out.println(userList.size());

		for(i=0;i<userList.size();i++){
			System.out.println(userList.get(i).getId()+", "+userList.get(i).getEmail()+", "+userList.get(i));
		}
		
		/*\\
		//6. 새로운 Event 2개 등록 및 각각 id 추출
		System.out.println("6. 새로운 Event 2개 등록 및 각각 id 추출");
		Event newEvent1 = new Event();
		//Date date1 = new Date("1988-03-28 20:30:00");
		//Calendar cal1 = Calendar.getInstance();
		//cal1.setTime(date1);
		//newEvent1.setWhen(cal1);
		newEvent1.setSummary("meeting");
		newEvent1.setDescription("team discussion");
		newEvent1.setOwner(newUser1);
		newEvent1.setAttendee(newUser2);
		System.out.println("****************");
		
		eventDao.createEvent(newEvent1);
		
		Event newEvent2 = new Event();
		
		//Date date2 = new Date("1988-03-28 20:30:00");
		//Calendar cal2 = Calendar.getInstance();
		//cal2.setTime(date2);
		//newEvent2.setWhen(cal2);
		newEvent2.setSummary("exercise");
		newEvent2.setDescription("soccer tournament");
		newEvent2.setOwner(newUser2);
		newEvent2.setAttendee(newUser1);
		System.out.println("****************");
		
		eventDao.createEvent(newEvent1);
		
		*/
		
		//7. 추출된 id와 함께 새로운 Event 2개를 DB에서 가져와 (getEvent 메소드 사용) 방금 추가한 2개의 이벤트와 내용 (when, summary, description, owner, attendee)이 일치하는 지 비교
		//8. 5개의 Event 모두 출력 (owner와 attendee는 해당 사용자의 이메일과 이름을 출력)
	}
}
