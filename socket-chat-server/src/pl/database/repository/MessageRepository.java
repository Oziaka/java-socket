package pl.database.repository;

import pl.model.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository {

   private Statement statement;

   public MessageRepository(Statement statement) {
      this.statement = statement;
   }

   public void save(Long senderId, Long receiverId, String text) {
      try {
         statement.execute("INSERT INTO message(sender_id, receiver_id,text) VALUES(" + senderId + "," + receiverId + ",'" + text + "')");
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
   }

   public List<Message> getAll(Long senderId, Long receiver_id) {
      try {
         List<Message> messages = new ArrayList<>();
         ResultSet resultSet = statement.executeQuery("SELECT (SELECT u.name FROM user u WHERE m.sender_id = u.id),(SELECT u.name FROM user u WHERE m.receiver_id = u.id), m.text FROM message m WHERE ((sender_id = " + senderId + " and receiver_id=" + receiver_id + ") or (sender_id = " + receiver_id + " and receiver_id=" + senderId + "))");
         while (resultSet.next())
            messages.add(new Message(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
         return messages;
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
      return null;
   }
}
