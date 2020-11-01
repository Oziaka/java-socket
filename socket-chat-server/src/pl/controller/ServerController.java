package pl.controller;

import pl.database.DatabaseInitializer;
import pl.model.Request;
import pl.model.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Statement;

public class ServerController {
   private int port;
   private ServerSocket serverSocket;
   private Socket clientSocket;
   private ActionController actionController;

   public ServerController(int port, Statement statement) {
      this.port = port;
      try {
         serverSocket = new ServerSocket(port);
      } catch (IOException e) {
         e.printStackTrace();
      }
      DatabaseInitializer databaseInitializer = new DatabaseInitializer(statement);
      this.actionController = new ActionController(statement);
   }

   public void run() throws IOException {
      while (true) {
         clientSocket = serverSocket.accept();
         ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
         ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
         try {
            Request input = (Request) in.readObject();
            Response response = actionController.doAction(input);
            out.writeObject(response);
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }
   }
}
