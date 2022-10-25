package org.example.dao;

import org.example.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao AwsUserDao(){
        ConnectionMaker connectionMaker = new AwsConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
}
