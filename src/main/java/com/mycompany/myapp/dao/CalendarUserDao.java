package com.mycompany.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.mycompany.myapp.domain.CalendarUser;

public interface CalendarUserDao {    
   
    public CalendarUser getUser(int id) throws ClassNotFoundException, SQLException;

    public CalendarUser findUserByEmail(String email);

    public List<CalendarUser> findUsersByEmail(String partialEmail);

    public int createUser(CalendarUser user);
    
    public void deleteAll();
}
