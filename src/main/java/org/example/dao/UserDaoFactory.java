package org.example.dao;

import org.example.domain.User;

public class UserDaoFactory {
    public UserDao userDao(){
        ConnectionMaker connectionMaker = new AwsConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);

        return userDao;
    }
}
