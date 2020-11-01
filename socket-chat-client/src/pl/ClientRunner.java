package pl;

import pl.model.Action;
import pl.service.ClientService;

public class ClientRunner {
   private static final int DEFAULT_PORT = 1337;
   private static final String DEFAULT_IP = "127.0.0.1";

   public static void main(String[] args) {
      int port = getPortFromArgsOrElseDefault(args);
      String ip = getIpFromArgsOrElseDefault(args);
      ClientService clientService = new ClientService(port, ip);
      ActionController actionController = new ActionController(clientService);
      System.out.println("Hi on chat on java sockets");
      while (true) {
         try {
            System.out.println("Choose something");
            Action action = actionController.chooseAction();
            actionController.doAction(action);
         } catch (Exception ignored) {
            ignored.printStackTrace();
         }
      }
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

   private static String getIpFromArgsOrElseDefault(String[] args) {
      String ip;
      try {
         ip = args[1];
      } catch (ArrayIndexOutOfBoundsException exception) {
         ip = DEFAULT_IP;
      }
      return ip;
   }
}
