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

<<<<<<< HEAD
    // --- constructors ---
    /*
	public JdbcCalendarUserDao() {
    		
    }
    */
=======
	// --- constructors ---
	public JdbcCalendarUserDao() {

	}

>>>>>>> ce2d7ae748ad952391b5793a9d10854e9028d8ab
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

<<<<<<< HEAD
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
=======
	// --- CalendarUserDao methods ---
	@Override
	public CalendarUser getUser(int id){
		Connection c;
		CalendarUser user = new CalendarUser();
		try {
			c = dataSource.getConnection();


			PreparedStatement ps = c.prepareStatement( "select * from calendar_users where id = ?");
			ps.setString(1, Integer.toString(id));

			ResultSet rs = ps.executeQuery();
			rs.next();

			user.setId(Integer.parseInt(rs.getString("id")) );
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));

			rs.close();
			ps.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public CalendarUser findUserByEmail(String email) {
		return null;
	}

	@Override
	public List<CalendarUser> findUsersByEmail(String email) {
		List<CalendarUser> calendarUsers = new ArrayList<CalendarUser>();
		Connection c;
		try {
			c = dataSource.getConnection();

			String sql_query;
			if(email == null)
				sql_query = "select * from calendar_users";
			else
				sql_query = "select * from calendar_users where email like '%"+email+"%'";
			PreparedStatement ps;

			ps = c.prepareStatement(sql_query);

			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendarUsers;
	}

	@Override
	public int createUser(final CalendarUser userToAdd){
		Connection c;
		int generatedId = 0; 
		try {
			c = dataSource.getConnection();

			PreparedStatement ps = c.prepareStatement( "insert into calendar_users(email, password, name) values(?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, userToAdd.getEmail());
			ps.setString(2, userToAdd.getPassword());
			ps.setString(3, userToAdd.getName());

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
	public void deleteAll() {
		// Assignment 2
	}
>>>>>>> ce2d7ae748ad952391b5793a9d10854e9028d8ab
}