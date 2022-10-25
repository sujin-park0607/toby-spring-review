package org.example.dao;
import org.example.domain.User;

import java.sql.*;

public class UserDao {

    private ConnectionMaker connectionMaker;


    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }


    public void jdbcContextWithStatementStrategy(StatementStrategy stmt)
            throws  SQLException, ClassNotFoundException{
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps != null){ try{ ps.close(); }catch (SQLException e){} }
            if(c != null){ try{ c.close(); }catch (SQLException e){} }
        }

    }
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        StatementStrategy st = new AddStrategy(user);
        jdbcContextWithStatementStrategy(st);
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = connectionMaker.makeConnection();

            ps = c.prepareStatement(
                    "select * from users where id = ?");
            ps.setString(1, id);

            rs = ps.executeQuery();

            User user = null;
            if(rs.next()){
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }

            return user;
        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){}
            }
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){}
            }
            if(c != null){
                try{
                    c.close();
                }catch (SQLException e){}
            }
        }
    }

    public void delete() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();

        StatementStrategy st = new DeleteAllStrategy();
        jdbcContextWithStatementStrategy(st);
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }
}