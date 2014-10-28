package com.mycompany.myapp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.stereotype.Repository;



import com.mycompany.myapp.dao.CalendarUserDao;
import com.mycompany.myapp.dao.EventDao;
import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;
import com.mycompany.myapp.domain.EventAttendee;
import com.mycompany.myapp.domain.EventLevel;

//@Service
@Repository
public class DefaultCalendarService implements CalendarService {
	@Autowired
    private EventDao eventDao;
	
	@Autowired
    private CalendarUserDao userDao;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<CalendarUser> calendarRowMapper;
	private RowMapper<Event> eventRowMapper;
	private RowMapper<EventAttendee> eventAttendeeRowMapper;
	
	public DefaultCalendarService() {
		calendarRowMapper = new RowMapper<CalendarUser>() {
			public CalendarUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				CalendarUser calendarUser = new CalendarUser();
				calendarUser.setId(rs.getInt("id"));
				calendarUser.setEmail(rs.getString("email"));
				calendarUser.setPassword(rs.getString("password"));
				calendarUser.setName(rs.getString("name"));

				return calendarUser;
			}
		};
		eventRowMapper = new RowMapper<Event>() {
			public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
				Event event = new Event();
				event.setId(rs.getInt("id"));
				Calendar when = Calendar.getInstance();
				when.setTimeInMillis(rs.getTimestamp("when").getTime());
				event.setWhen(when);
				event.setSummary(rs.getString("summary"));
				event.setDescription(rs.getString("description"));
				event.setOwner(userDao.findUser(rs.getInt("owner")));
				event.setNumLikes(rs.getInt("num_likes"));  						// Updated by Assignment 3
				event.setEventLevel(EventLevel.valueOf(rs.getInt("event_level")));	// Updated by Assignment 3
				return event;
			}
		};
		eventAttendeeRowMapper = new RowMapper<EventAttendee>() {
			public EventAttendee mapRow(ResultSet rs, int rowNum) throws SQLException {
				EventAttendee eventAttendeeList = new EventAttendee();
				// TODO Assignment 3
				eventAttendeeList.setId(rs.getInt("id"));
				eventAttendeeList.setEvent(eventDao.findEvent(rs.getInt("event_id")));
				eventAttendeeList.setAttendee(userDao.findUser(rs.getInt("attendee")));
				return eventAttendeeList;
			}
		};
	}
	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	
	
	
	/* CalendarUser */
	@Override
    public CalendarUser getUser(int id) {
		// TODO Assignment 3
		String sql_query = "select * from calendar_users where id = ?";
		return this.jdbcTemplate.queryForObject(sql_query, new Object[] {id}, calendarRowMapper);
	}
	
	@Override
    public CalendarUser getUserByEmail(String email) {
		// TODO Assignment 3
		String sql_query = "select * from calendar_users where email = ?";
		return this.jdbcTemplate.queryForObject(sql_query, new Object[] {email}, calendarRowMapper);
	}
	
	@Override
    public List<CalendarUser> getUsersByEmail(String partialEmail) {
		// TODO Assignment 3
		String sql_query;
		if(partialEmail == null)
			sql_query = "select * from calendar_users";
		else
			sql_query = "select * from calendar_users where email like '%"+partialEmail+"%'";

		return this.jdbcTemplate.query(sql_query, calendarRowMapper);
	}
	
	@Override
    public int createUser(final CalendarUser user) {
		// TODO Assignment 3
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("insert into calendar_users(email, password, name) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getName());

				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
    
	@Override
    public void deleteAllUsers() {
			// TODO Assignment 3
			String sql = "delete from calendar_users";
			this.jdbcTemplate.update(sql);
		}
		
    
	
    /* Event */
	@Override
    public Event getEvent(int eventId) {
		// TODO Assignment 3
		String sql_query = "select * from events where id = ?";
		return this.jdbcTemplate.queryForObject(sql_query, new Object[] {eventId}, eventRowMapper);
	}
	
	@Override
    public List<Event> getEventForOwner(int ownerUserId) {
		// TODO Assignment 3
		String sql_query = "select * from events where owner = ?";
		return this.jdbcTemplate.query(sql_query, new Object[] {ownerUserId}, eventRowMapper);
	}

	@Override
    public List<Event> getAllEvents() {
		// TODO Assignment 3
		String sql_query = "select * from events";
		return this.jdbcTemplate.query(sql_query, eventRowMapper);
	}

	@Override
    public int createEvent(final Event event) {
		// TODO Assignment 3
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("insert into events(`when`, summary, description, owner, num_likes, event_level) values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				
				Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis()); /* Updated by Assignment 3 */ 
				ps.setTimestamp(1, timestamp);
				ps.setString(2, event.getSummary());
				ps.setString(3, event.getDescription());
				ps.setInt(4, event.getOwner().getId());
				ps.setInt(5, event.getNumLikes());      		/* Updated by Assignment 3 */
				ps.setInt(6, event.getEventLevel().intValue());	/* Updated by Assignment 3 */
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
		/*
		if (event.getEventLevel() == null) {
			event.setEventLevel(EventLevel.NORMAL);
		}
		
		return -1;
		*/
	}
    
	@Override
    public void deleteAllEvents() {
		// TODO Assignment 3
		String sql = "delete from events";
		this.jdbcTemplate.update(sql);
	}

	
	
    /* EventAttendee */
	@Override
	public List<EventAttendee> getEventAttendeeByEventId(int eventId) {
		// TODO Assignment 3
		String sql_query = "select * from events_attendees where event_id = "+eventId+"";
		return this.jdbcTemplate.query(sql_query, eventAttendeeRowMapper);
	}

	@Override
	public List<EventAttendee> getEventAttendeeByAttendeeId(int attendeeId) {
		// TODO Assignment 3
		String sql_query = "select * from events_attendees where attendee = "+attendeeId+"";
		return this.jdbcTemplate.query(sql_query, eventAttendeeRowMapper);
	}

	@Override
	public int createEventAttendee(final EventAttendee eventAttendee) {
		// TODO Assignment 3
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("insert into events_attendees(event_id, attendee) values(?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, eventAttendee.getEvent().getId());
				ps.setInt(2, eventAttendee.getAttendee().getId());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void deleteEventAttendee(int id) {
		// TODO Assignment 3
		String sql_query = "delete from events_attendees where id = ?";
		this.jdbcTemplate.query(sql_query, new Object[] {id}, eventAttendeeRowMapper);
	}

	@Override
	public void deleteAllEventAttendees() {
		// TODO Assignment 3
		String sql = "delete from events_attendees";
		this.jdbcTemplate.update(sql);
	}
	
	
	
	/* upgradeEventLevels */
	@Override
	public void upgradeEventLevels() throws Exception{
		// TODO Assignment 3
		// 트랜잭션 관련 코딩 필요함
		TransactionStatus status = 
				this.transactionManager.getTransaction(new DefaultTransactionDefinition());
			try {
				List<Event> events = eventDao.findAllEvents();
				for (Event event : events) {
					if (canUpgradeEventLevel(event)) {
						upgradeEventLevel(event);
					}
				}
				this.transactionManager.commit(status);
			} catch (RuntimeException e) {
				this.transactionManager.rollback(status);
				throw e;
			}
	}

	@Override
	public boolean canUpgradeEventLevel(Event event) {
		// TODO Assignment 3
		EventLevel currentLevel = event.getEventLevel();
		switch(currentLevel) {                                   
			case NORMAL: return (event.getNumLikes() >= 10); 
			case HOT: return false;
			default: throw new IllegalArgumentException("Unknown Level: " + currentLevel); 
		}
	}
	
	@Override
	public void upgradeEventLevel(Event event) {
		event.upgradeLevel();
		eventDao.udpateEvent(event);
	}
}