package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.CalendarUser;

@Repository
public class JdbcCalendarUserDao implements CalendarUserDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public JdbcCalendarUserDao() {

	}
	
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

    // --- CalendarUserDao methods ---
    @Override
    public CalendarUser getUser(int id) {
    	Connection c;// = dataSource.getConnection();
		CalendarUser user = new CalendarUser();
		
		try{
			c = dataSource.getConnection();
			PreparedStatement ps = c.prepareStatement( "select * from calendar.calendar_users where id = ?");
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			user.setId(Integer.parseInt(rs.getString("id")));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			
			
			rs.close();
			ps.close();
			c.close();  	
			
		}catch(SQLException e){
			e.printStackTrace();
		}

    	
    	return user;
    }

    @Override
    public CalendarUser findUserByEmail(String email){
    	Connection c;
    	try{
    		c= dataSource.getConnection();
    		
    		PreparedStatement ps = c.prepareStatement("select * from calendar.calendar_users where email = ?");
    		ps.setString(1, email);
    		
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()){
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
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	
    	return null;
    }

    @Override
    public List<CalendarUser> findUsersByEmail(String email) {
    	
    	List<CalendarUser> calendarUsers = new ArrayList<CalendarUser>();
    	Connection c;
    	
    	try{
    		c = dataSource.getConnection();
    		String sql_query;
    		if(email == null)
    			sql_query = "select * from calendar_users";
    		else
    			sql_query = "select * from calendar_users where email like '%"+email+"%'";
    		
    		
    		PreparedStatement ps = c.prepareStatement(sql_query);
    		ResultSet rs = ps.executeQuery();
    	
    		while(rs.next()){
    			CalendarUser user = new CalendarUser();
    			
    			user.setId(Integer.parseInt(rs.getString("id")) );
    			user.setEmail(rs.getString("email"));
    			user.setPassword(rs.getString("password"));
    			user.setName(rs.getString("name"));
    			
    			calendarUsers.add(user);
    		}
    		rs.close();
    		ps.close();
    		c.close();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return calendarUsers;
    }
    
    @Override
    public int createUser(final CalendarUser userToAdd){
    	this.jdbcTemplate.update("insert into calendar.calendar_users(email, password, name) values(?,?,?)", userToAdd.getEmail(), userToAdd.getPassword(), userToAdd.getName());
    	return 0;
    	/*Connection c;
    	
    	int generateId = 0;	
    	try{
    		c = dataSource.getConnection();
    		
    		PreparedStatement ps = c.prepareStatement("insert into calendar.calendar_users(email, password, name) values(?,?,?)");
    		ps.setString(1, userToAdd.getEmail());
    		ps.setString(2, userToAdd.getPassword());
    		ps.setString(3, userToAdd.getName());	
    		
    		ps.executeUpdate();
    		
    		ResultSet rs = ps.getGeneratedKeys();
    		
    		if(rs.next()){
    			generateId = rs.getInt(1);
    		}
    		
    		rs.close();
    		ps.close();
    		c.close();
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return generateId;
    	*/
    }

	@Override
	public void deleteAll() {
		this.jdbcTemplate.update(new PreparedStatementCreator(){
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
					return con.prepareStatement("delete from calendar.calendar_users");
				}
			});
		/*
		Connection c;
    
    	try{
    		c = dataSource.getConnection();
    		
    		PreparedStatement ps;// = c.prepareStatement("delete from calendar.calendar_users");
    		ps = makeStatement(c);
    		ps.executeUpdate();
    		
    		ps.close();
    		c.close();
    		
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	*/
	}
	
}