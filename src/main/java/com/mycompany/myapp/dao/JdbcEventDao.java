package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.util.Calendar;
import java.util.Date;
=======
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
>>>>>>> ce2d7ae748ad952391b5793a9d10854e9028d8ab
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;


@Repository
public class JdbcEventDao implements EventDao {
	private DataSource dataSource;

<<<<<<< HEAD
    // --- constructors ---
    /*
    public JdbcEventDao() {
    }
	*/
    
=======
	// --- constructors ---
	public JdbcEventDao() { 
	}

>>>>>>> ce2d7ae748ad952391b5793a9d10854e9028d8ab
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

<<<<<<< HEAD
    // --- EventService ---
    @Override
    public Event getEvent(int eventId) throws SQLException {
    	Connection c = dataSource.getConnection();
		
		//PreparedStatement ps = c.prepareStatement( "select id, when, summary, description, owner, attendee from calendar.events where id = ?");
    	PreparedStatement ps = c.prepareStatement( "select id, summary, description from calendar.events where id = ?");
    	ps.setInt(1, eventId);
		ResultSet rs = ps.executeQuery();	
		rs.next();
					
		Event event = new Event();
	
		event.setId(rs.getInt(1));
		//event.setWhen(rs.get);
		event.setSummary(rs.getString(2));
		event.setDescription(rs.getString(3));
		/*
		PreparedStatement ps2 = c.prepareStatement( "select id from calendar.calendar_users where id = ?");
		ps2.setInt(1, rs.getInt(5));
		ResultSet rs2 = ps2.executeQuery();
		rs2.next();
	
		event.setOwner(rs2.getInt(1));
		*/		
		rs.close();
		ps.close();
		c.close();  	
	
        return event;
    }

    @Override
    public int createEvent(final Event event) throws SQLException {
    	Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("insert into calendar.events(summary, description, owner, attendee) values(?,?,?,?)");

		//ps.setString(1, event.getWhen().toString());
		ps.setString(1, event.getSummary());
		ps.setString(2, event.getDescription());
		ps.setObject(3, event.getOwner());
		ps.setObject(4, event.getAttendee());
		
		ps.executeUpdate();

		ps.close();
		c.close();	
    	return 0;
    }

    @Override
    public List<Event> findForUser(int userId) {
        return null;
    }

    @Override
    public List<Event> getEvents() {
        return null;
    }

    /*
    private static final String EVENT_QUERY = "select e.id, e.summary, e.description, e.when, " +
            "owner.id as owner_id, owner.email as owner_email, owner.password as owner_password, owner.name as owner_name, " +
            "attendee.id as attendee_id, attendee.email as attendee_email, attendee.password as attendee_password, attendee.name as attendee_name " +
            "from events as e, calendar_users as owner, calendar_users as attendee " +
            "where e.owner = owner.id and e.attendee = attendee.id";
     */
=======
	// --- EventService ---
	@Override
	public Event getEvent(int eventId) {
		ApplicationContext context = new GenericXmlApplicationContext("com/mycompany/myapp/applicationContext.xml");;
		CalendarUserDao calendarUserDao = context.getBean("calendarUserDao", JdbcCalendarUserDao.class);

		Event event = new Event();

		Connection c;
		try {
			c = dataSource.getConnection();


			PreparedStatement ps = c.prepareStatement( "select * from events where id = ?");
			ps.setString(1, Integer.toString(eventId));

			ResultSet rs = ps.executeQuery();
			rs.next();

			event.setId(Integer.parseInt(rs.getString("id")));
			 
			Calendar when = Calendar.getInstance();
			when.setTimeInMillis(rs.getTimestamp("when").getTime());
			event.setWhen(when);
			event.setSummary(rs.getString("summary"));
			event.setDescription(rs.getString("description"));
			CalendarUser owner = calendarUserDao.getUser(rs.getInt("owner"));
			event.setOwner(owner);
			CalendarUser attendee = calendarUserDao.getUser(rs.getInt("attendee"));
			event.setAttendee(attendee);

			rs.close();
			ps.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event;
	}

	@Override
	public int createEvent(final Event event) {
		Connection c;
		int generatedId = 0; 
		try {
			c = dataSource.getConnection();

			PreparedStatement ps = c.prepareStatement( "insert into events(`when`, summary, description, owner, attendee) values(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			
			Timestamp timestamp = new Timestamp(event.getWhen().getTimeInMillis()); 
			
			ps.setTimestamp(1, timestamp);
			ps.setString(2, event.getSummary());
			ps.setString(3, event.getDescription());
			ps.setInt(4, event.getOwner().getId());
			ps.setInt(5, event.getAttendee().getId());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();

			if(rs.next())
			{
				generatedId = rs.getInt(1);
			}
			rs.close();
			ps.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generatedId;
	}

	@Override
	public List<Event> findForOwner(int ownerUserId) {
		// Assignment 2
		return null;
	}

	@Override
	public List<Event> getEvents(){
		ApplicationContext context = new GenericXmlApplicationContext("com/mycompany/myapp/applicationContext.xml");;

		CalendarUserDao calendarUserDao = context.getBean("calendarUserDao", JdbcCalendarUserDao.class);

		List<Event> list = new ArrayList<Event>();

		Connection c;
		try {
			c = dataSource.getConnection();


			PreparedStatement ps = c.prepareStatement( "select * from events");

			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Event event = new Event();
				event.setId(Integer.parseInt(rs.getString("id")));
				Calendar when = Calendar.getInstance();
				when.setTimeInMillis(rs.getTimestamp("when").getTime());
				event.setWhen(when);
				event.setSummary(rs.getString("summary"));
				event.setDescription(rs.getString("description"));
				CalendarUser owner = calendarUserDao.getUser(rs.getInt("owner"));
				event.setOwner(owner);
				CalendarUser attendee = calendarUserDao.getUser(rs.getInt("attendee"));
				event.setAttendee(attendee);

				list.add(event);
			}
			rs.close();
			ps.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public void deleteAll() {
		// Assignment 2
	}
>>>>>>> ce2d7ae748ad952391b5793a9d10854e9028d8ab
}
