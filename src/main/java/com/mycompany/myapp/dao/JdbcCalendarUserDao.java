package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.CalendarUser;

@Repository
public class JdbcCalendarUserDao implements CalendarUserDao {

	private DataSource dataSource;

    // --- constructors ---
    /*
	public JdbcCalendarUserDao() {
    		
    }
    */
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

    // --- CalendarUserDao methods ---
    @Override
    public CalendarUser getUser(int id) throws SQLException {
    	Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement( "select id, email, name from calendar.calendar_users where id = ?");
		ps.setInt(1, id);
		
		
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
		CalendarUser user = new CalendarUser();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString(2));
		user.setPassword(rs.getString(3));
		
		rs.close();
		ps.close();
		c.close();  	
    	
    	return user;
    }

    @Override
    public CalendarUser findUserByEmail(String email) throws SQLException {
    	Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("select id, email, password, name from calendar.calendar_users where email = ?");
		ps.setString(1, email);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		CalendarUser user = new CalendarUser();
		user.setId(rs.getInt(1));
		user.setEmail(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setName(rs.getString(4));
		
		rs.close();
		ps.close();
		c.close(); 
		
    	return user;
    }

    @Override
    public List<CalendarUser> findUsersByEmail(String email) throws SQLException {
    	Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("select id, email, password, name from calendar.calendar_users");
		ResultSet rs = ps.executeQuery();
		
		CalendarUser user = new CalendarUser();
		List<CalendarUser> userList= new ArrayList<CalendarUser>();
		
		while(rs.next()){
			user.setId(rs.getInt(1));
			user.setEmail(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setName(rs.getString(4));
			
			userList.add(user);
		}
		
    	return userList;
    }
    @Override
    public List<CalendarUser> getUserList() throws SQLException {
    	Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("select id, email, password, name from calendar.calendar_users");
		ResultSet rs = ps.executeQuery();
		
		
		
		List<CalendarUser> userList= new ArrayList<CalendarUser>();
		
		while(rs.next()){
			CalendarUser user = new CalendarUser();
			user.setId(rs.getInt(1));
			user.setEmail(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setName(rs.getString(4));
			userList.add(user);
		}
		
		
    	return userList;
    }
    @Override
    public int createUser(final CalendarUser userToAdd) throws SQLException {
    	Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("insert into calendar.calendar_users(email, password, name) values(?,?,?)");
		
		ps.setString(1, userToAdd.getEmail());
		ps.setString(2, userToAdd.getPassword());
		ps.setString(3, userToAdd.getName());	

		ps.executeUpdate();

		ps.close();
		c.close();	
    	return 0;
    }
}