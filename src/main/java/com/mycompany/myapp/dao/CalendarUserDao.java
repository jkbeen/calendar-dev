package com.mycompany.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.mycompany.myapp.domain.CalendarUser;

public interface CalendarUserDao {
<<<<<<< HEAD
	
    CalendarUser getUser(int id) throws SQLException;
    
    CalendarUser findUserByEmail(String email) throws SQLException;
    
    List<CalendarUser> findUsersByEmail(String partialEmail) throws SQLException;
    
    int createUser(CalendarUser user) throws SQLException;

	List<CalendarUser> getUserList() throws SQLException;
=======
    public CalendarUser getUser(int id);

    public CalendarUser findUserByEmail(String email);

    public List<CalendarUser> findUsersByEmail(String partialEmail);

    public int createUser(CalendarUser user);
    
    public void deleteAll();
>>>>>>> ce2d7ae748ad952391b5793a9d10854e9028d8ab
}
