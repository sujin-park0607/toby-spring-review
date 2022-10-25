package org.example.dao;

import com.mysql.cj.xdevapi.AddStatement;
import org.example.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStrategy implements StatementStrategy {
    User user;

    public AddStrategy(User user){
        this.user = user;
    }
    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        return ps;
    }
}
