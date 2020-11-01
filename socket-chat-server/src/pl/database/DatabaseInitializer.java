package pl.database;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

   private String createUserTable = "CREATE TABLE IF NOT EXISTS user ( id integer PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20) NOT NULL, password VARCHAR(20))";
   private String messageTable = "CREATE TABLE IF NOT EXISTS message ( sender_id integer , receiver_id integer, text LONGTEXT )";

   private Statement statement;

   public DatabaseInitializer(Statement statement) {
      this.statement = statement;
      this.init();
   }

   private void init() {
      try {
         statement.execute(createUserTable);
         statement.execute(messageTable);
      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }
   }
}
