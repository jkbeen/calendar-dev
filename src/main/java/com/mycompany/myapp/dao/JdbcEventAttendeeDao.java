package com.mycompany.myapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.EventAttendee;

@Repository
public class JdbcEventAttendeeDao implements EventAttendeeDao {
	private JdbcTemplate jdbcTemplate;

	private RowMapper<EventAttendee> rowMapper;

	@Autowired
	private CalendarUserDao eventDao;
	
	@Autowired
	private CalendarUserDao calendarUserDao;
	
	// --- constructors ---
	public JdbcEventAttendeeDao() {
		rowMapper = new RowMapper<EventAttendee>() {
			public EventAttendee mapRow(ResultSet rs, int rowNum) throws SQLException {
				EventAttendee eventAttendeeList = new EventAttendee();

				/* TODO Assignment 3 */
				
				return eventAttendeeList;
			}
		};
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<EventAttendee> findEventAttendeeByEventId(int eventId) {
		// TODO Assignment 3
		return null;
	}

	@Override
	public List<EventAttendee> findEventAttendeeByAttendeeId(int attendeeId) {
		// TODO Assignment 3
		return null;
	}

	@Override
	public int createEventAttendee(EventAttendee eventAttendee) {
		// TODO Assignment 3
		return -1;
	}

	@Override
	public void deleteEventAttendee(int id) {
		// TODO Assignment 3
		
	}

	@Override
	public void deleteAll() {
		// TODO Assignment 3
		
	}
}