package org.example.dao;
import org.example.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {

    private DataSource dataSource;
    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public void deleteAll() throws SQLException, ClassNotFoundException {
        this.jdbcTemplate.update("delete from users");
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        this.jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)", user.getId(), user.getName(), user.getPassword());

    }
//        this.jdbcContext.workWithStatementStrategy(
//                new StatementStrategy() {
//                    @Override
//                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//                        PreparedStatement ps = c.prepareStatement(
//                                "insert into users(id, name, password) values(?, ?, ?)");
//                        ps.setString(1, user.getId());
//                        ps.setString(2, user.getName());
//                        ps.setString(3, user.getPassword());
//                        return ps;
//                    }
//                }
//        );

    public User get(String id) throws ClassNotFoundException, SQLException {
        String sql = "select * from users where id = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
//        Connection c = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            c = dataSource.getConnection();
//
//            ps = c.prepareStatement(
//                    "select * from users where id = ?");
//            ps.setString(1, id);
//
//            rs = ps.executeQuery();
//
//            User user = null;
//            if(rs.next()){
//                user = new User();
//                user.setId(rs.getString("id"));
//                user.setName(rs.getString("name"));
//                user.setPassword(rs.getString("password"));
//            }
//
//            return user;
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            if(rs != null){
//                try{
//                    rs.close();
//                }catch (SQLException e){}
//            }
//            if(ps != null){
//                try{
//                    ps.close();
//                }catch (SQLException e){}
//            }
//            if(c != null){
//                try{
//                    c.close();
//                }catch (SQLException e){}
//            }
//        }



    public int getCount() throws SQLException, ClassNotFoundException {
        int rr = this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
        return rr;
//        Connection c = dataSource.getConnection();
//        PreparedStatement ps = c.prepareStatement("select count(*) from users");
//
//        ResultSet rs = ps.executeQuery();
//        rs.next();
//        int count = rs.getInt(1);
//
//        rs.close();
//        ps.close();
//        c.close();
//
//        return count;
    }

    public List<User> getAll(){
        String sql = "select * from users order by id";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.query(sql, rowMapper);
    }
}