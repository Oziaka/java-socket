package pl;

import pl.controller.ServerController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRunner {
   private static final int DEFAULT_PORT = 1337;

   public static void main(String[] args) throws ClassNotFoundException, SQLException {
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/socket-chat?serverTimezone=UTC", "root", "admin");
      int port = getPortFromArgsOrElseDefault(args);
      ServerController serverController = new ServerController(port,connection.createStatement());
      Class.forName("com.mysql.jdbc.Driver");
      ExecutorService executorService = Executors.newFixedThreadPool(50);
      executorService.submit(() -> {
         try {
            serverController.run();
         } catch (IOException e) {
            e.printStackTrace();
         }
      });
   }


   private static int getPortFromArgsOrElseDefault(String[] args) {
      int port;
      try {
         port = Integer.parseInt(args[0]);
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {
         port = DEFAULT_PORT;
      }
      return port;
   }
}
