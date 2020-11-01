package pl.service;

import pl.model.Action;
import pl.model.Request;
import pl.model.Response;
import pl.model.User;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientService implements Closeable {

   private int port;
   private String ip;

   public ClientService(int port, String ip) {
      this.port = port;
      this.ip = ip;
   }

   private Socket clientSocket;
   private ObjectOutputStream out;
   private ObjectInputStream in;

   public void run() throws IOException {
      clientSocket = new Socket(ip, port);
      out = new ObjectOutputStream(clientSocket.getOutputStream());
      in = new ObjectInputStream(clientSocket.getInputStream());
   }

   public Response sendAsGuest(Action action, Object object) {
      return sendAsUser(action, object, new User("Guset", null));
   }

   public Response sendAsUser(Action action, Object object, User user) {
      try {
         run();
         out.writeObject(new Request(action, object, user));
         return (Response) in.readObject();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }

   public void close() throws IOException {
      in.close();
      out.close();
      clientSocket.close();
   }
}
