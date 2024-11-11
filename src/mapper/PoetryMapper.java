package mapper;

import model.Poetry;
import utils.DBUtil;
import utils.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PoetryMapper {

    // 获取所有诗词
    public List<Poetry> querytAllPoetry() throws Exception {
        // 定义sql语句
        String sql = "select * from poetry";
        // 执行sql语句
        Statement statement = DBUtil.getConnect().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        // 讲结果集封装到列表中
        List<Poetry> lists = new ArrayList<>();
        while (resultSet.next()){
            String poetryId = resultSet.getString("poetry_id");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            String poemsName = resultSet.getString("poems_name");
            String dynastyName = resultSet.getString("dynasty_name");
            String category = resultSet.getString("category");
            lists.add(new Poetry(poetryId, title, content, poemsName, dynastyName, category));
        }
        return lists;
    }

    // 根据特定条件查询
    public List<Poetry> conditionalSearch(int searchIndex, String searchText) throws SQLException {
        String searchConditional = getSearchConditionalByIndex(searchIndex);
        String sql = "select * from poetry where " + searchConditional + " like ?";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, "%" + searchText + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        // 讲结果集封装到列表中
        List<Poetry> lists = new ArrayList<>();
        while (resultSet.next()){
            String poetryId = resultSet.getString("poetry_id");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            String poemsName = resultSet.getString("poems_name");
            String dynastyName = resultSet.getString("dynasty_name");
            String category = resultSet.getString("category");
            lists.add(new Poetry(poetryId, title, content, poemsName, dynastyName, category));
        }
        return lists;
    }

    public int delePoetry(String poetryId) throws SQLException {
        String sql = "delete from poetry where poetry_id = ?";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, poetryId);
        return preparedStatement.executeUpdate();
    }

    public int addPoetry(String title, String content, String poemsName, String dynastyName, String category) throws SQLException {
        String sql = "insert into poetry(title, content, poems_name, dynasty_name, category) values (?,?,?,?,?)";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, content);
        preparedStatement.setString(3, poemsName);
        preparedStatement.setString(4, dynastyName);
        preparedStatement.setString(5, category);
        return preparedStatement.executeUpdate();
    }

    public int editPoetry(String title, String content, String poemsName, String dynastyName, String category, String poetryId) throws SQLException {
        String sql = "update poetry set title = ?, content = ?, poems_name = ?, dynasty_name = ? , category = ? where poetry_id =?";
        PreparedStatement preparedStatement = DBUtil.getConnect().prepareStatement(sql);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, content);
        preparedStatement.setString(3, poemsName);
        preparedStatement.setString(4, dynastyName);
        preparedStatement.setString(5, category);
        preparedStatement.setString(6, poetryId);
        return preparedStatement.executeUpdate();
    }

    public String getSearchConditionalByIndex(int index) {
        return switch (index) {
            case 0 -> "poems_name";
            case 1 -> "title";
            case 2 -> "dynasty_name";
            case 3 -> "category";
            case 4 -> "content";
            default -> "";
        };
    }
}
