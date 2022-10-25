package org.example.dao;

import org.example.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setup(){
        user1 = new User("18","sujin","11111");
        user2 = new User("2","minjang","2222");
        user3 = new User("3","suwon","3333");
    }

    @Test
    @DisplayName("Add와 Get함수 테스트")
    void addAndGet() throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDaoFactory().userDao();

        dao.add(user1);
        System.out.println("등록 성공");

        User testUser = dao.get("18");
        Assertions.assertEquals(user1.getName(), testUser.getName());

    }
}