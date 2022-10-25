package org.example.dao;

import org.example.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    private User user1;
    private User user2;
    private User user3;
    private UserDao dao;

    @BeforeEach
    void setup() throws SQLException, ClassNotFoundException {
        dao = context.getBean("awsUserDao", UserDao.class);

        dao.deleteAll();
        user1 = new User("1","sujin","11111");
        user2 = new User("2","minjang","2222");
        user3 = new User("3","suwon","3333");
    }

    @Test
    @DisplayName("Add와 Get함수 테스트")
    void addAndGet() throws SQLException, ClassNotFoundException {
        dao.add(user1);
        System.out.println("등록 성공");

        User testUser = dao.get("1");
        Assertions.assertEquals(user1.getName(), testUser.getName());
    }

    @Test
    @DisplayName("delete함수 테스트")
    void deleteTest() throws SQLException, ClassNotFoundException {

        dao.add(user1);
        dao.add(user2);
        dao.add(user3);

        dao.deleteAll();
        int count = dao.getCount();
        Assertions.assertEquals(0, count);
    }

    @Test
    @DisplayName("getcount함수 테스트")
    void getCount() throws SQLException, ClassNotFoundException {

        dao.add(user1);
        dao.add(user2);
        dao.add(user3);

        int count = dao.getCount();
        Assertions.assertEquals(3, count);
    }

    @Test
    @DisplayName("user이 null일때 get할 경우 에러")
    void getTest() throws SQLException, ClassNotFoundException {
        assertThrows(EmptyResultDataAccessException.class, ()->
                dao.get("1"));

    }

    @Test
    @DisplayName("없을 때 빈 리스트린턴 하는지, 있을 때 개수만큼 리턴 하는지")
    void getAllTest() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        List<User> users = dao.getAll();
        assertEquals(0, users.size());
        dao.add(user1);
        dao.add(user2);
        dao.add(user3);
        assertEquals(3, users.size());

    }
}