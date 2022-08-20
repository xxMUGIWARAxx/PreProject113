package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }


    @Override
    public void createUsersTable() throws SQLException {
        // All is OK
        PreparedStatement preparedStatement = null;
        String sql = "CREATE TABLE  Users (id MEDIUMINT NOT NULL AUTO_INCREMENT, FIRST_NAME CHAR(30) NOT NULL, last_name CHAR(30) NOT NULL,ahe byte not null , PRIMARY KEY (id))";
        preparedStatement = connection.prepareStatement(sql);
        if(preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {
        // All is OK
        PreparedStatement preparedStatement = null;
        String sql = "DROP TABLE Users";
        preparedStatement = connection.prepareStatement(sql);
        if(preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO Users (FIRST_NAME, LAST_NAME, AGE)VALUES(?, ?, ?, ?)";
        User user = new User();
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        // All is OK
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM Users WHERE ID = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LAST_NAME"));
                user.setAge(resultSet.getByte("AGE"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE * FROM Users ";
        preparedStatement = connection.prepareStatement(sql);
        if(preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
