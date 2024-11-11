package mapper;

import model.User;
import utils.DBUtil;
import utils.StringUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    // 查询全部User
    public List<User> selectAll() throws Exception {
        // 定义sql语句
        String sql = "select * from user";
        // 执行sql语句
        Statement statement = DBUtil.getConnect().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        // 讲结果集封装到列表中
        List<User> lists = new ArrayList<>();
        while (resultSet.next()){
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String userType = resultSet.getString("user_type");
            User user = new User(name, password, userType);
            lists.add(user);
        }
        return lists;
    }

    // 登录操作
    public User loginAction(String name, String password, String userType) throws SQLException {
        String sql = "select * from user where name = ? and password = ? and user_type= ?";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, StringUtil.md5(password));
        preparedStatement.setString(3, userType);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()){
            user = new User(resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("user_type"));
        }
        return user;
    }

    public User queryUserByName(String name) throws SQLException {
        String sql = "select * from user where name = ?";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()){
            user = new User(resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("user_type"));
        }
        return user;
    }


    public int registerAction(String name, String password) throws SQLException {
        String sql = "insert into user values (?,?,?)";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, StringUtil.md5(password));
        preparedStatement.setString(3, "普通用户");
        return preparedStatement.executeUpdate();
    }
}
