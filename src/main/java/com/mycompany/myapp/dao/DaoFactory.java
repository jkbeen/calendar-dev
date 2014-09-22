package com.mycompany.myapp.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


@Configuration
public class DaoFactory {
	
	@Bean
	public JdbcCalendarUserDao jdbcCalendarUserDao() {
		JdbcCalendarUserDao userDao = new JdbcCalendarUserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	@Bean
	public JdbcEventDao jdbcEventDao() {
		JdbcEventDao eventDao = new JdbcEventDao();
		eventDao.setDataSource(dataSource());
		return eventDao;
	}
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost/springbook");
		dataSource.setUsername("spring");
		dataSource.setPassword("book");
		
		return dataSource;
	}
}
