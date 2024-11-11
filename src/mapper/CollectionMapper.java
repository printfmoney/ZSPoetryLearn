package mapper;

import model.Collection;
import utils.DBUtil;
import utils.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CollectionMapper {

    public List<Collection> queryAllCollection(String userName) throws SQLException {
        String sql = "select * from collection where user_name = ?";
        // 执行sql语句
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();
        // 讲结果集封装到列表中
        List<Collection> lists = new ArrayList<>();
        while (resultSet.next()){
            String name = resultSet.getString("user_name");
            String poetryId = resultSet.getString("poetry_id");
            lists.add(new Collection(name, poetryId));
        }
        return lists;
    }

    public Collection queryStatus(String userName, String poetryId) throws SQLException {
        String sql = "select * from collection where user_name = ? and poetry_id = ?";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, poetryId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Collection collection = null;
        if (resultSet.next()){
            collection = new Collection(resultSet.getString("user_name"), resultSet.getString("poetry_id"));
        }
        return collection;
    }

    public int addCollectionAction(String userName, String poetryId) throws SQLException {
        String sql = "insert into collection values (?,?)";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, poetryId);
        return preparedStatement.executeUpdate();
    }

    public int removeCollectionAction(String userName, String poetryId) throws SQLException {
        String sql = "delete from collection where user_name = ? and poetry_id = ?";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, poetryId);
        return preparedStatement.executeUpdate();
    }
}
