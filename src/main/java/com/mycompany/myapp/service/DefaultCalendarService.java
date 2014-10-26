package com.mycompany.myapp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<CalendarUser> calendarRowMapper;
	private RowMapper<Event> eventRowMapper;
	private RowMapper<EventAttendee> eventAttendeeRowMapper;
	
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