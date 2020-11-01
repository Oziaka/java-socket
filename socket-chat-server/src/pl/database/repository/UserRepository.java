package pl.database.repository;

import pl.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
   private Statement statement;

   public UserRepository(Statement statement) {
      this.statement = statement;
   }

   public boolean isuserExist(User user) {
      try {
         ResultSet resultSet = statement.executeQuery("SELECT 0 < (SELECT count(*) FROM user u WHERE u.name = '" + user.getName() + "')");
         if (resultSet.next())
            return resultSet.getBoolean(1);
      } catch (SQLException exception) {
         exception.printStackTrace();
      }
      return false;
   }

   public void save(User user) {
      try {
         statement.execute("INSERT INTO user (name, password) VALUES ('" + user.getName() + "','" + user.getPassword() + "')");
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
   }

   public boolean isCorrectUser(User user) {
      try {
         ResultSet resultSet = statement.executeQuery("Select 1 = (SELECT count(*) FROM user u WHERE u.name ='" + user.getName() + "' and u.password='" + user.getPassword() + "')");
         if (resultSet.next())
            return resultSet.getBoolean(1);
      } catch (SQLException throwables) {
      }
      return false;

   }

   public User get(User user) {
      try {
         ResultSet resultSet = statement.executeQuery("SELECT * FROM user u WHERE u.name='" + user.getName() + "'");
         while (resultSet.next())
            return new User(resultSet.getLong(1), resultSet.getString(2), null);
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
      return null;
   }

   public List<User> getUsers() {
      try {
         List<User> users = new ArrayList<>();
         ResultSet resultSet = statement.executeQuery("SELECT  u.name FROM user u");
         while (resultSet.next())
            users.add(new User(resultSet.getString(1)));
         return users;
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
      return null;
   }
}
