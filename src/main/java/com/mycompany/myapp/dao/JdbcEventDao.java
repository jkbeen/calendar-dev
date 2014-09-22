package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

@Repository
public class JdbcEventDao implements EventDao {
    private DataSource dataSource;

    // --- constructors ---
    /*
    public JdbcEventDao() {
    }
	*/
    
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

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
}
