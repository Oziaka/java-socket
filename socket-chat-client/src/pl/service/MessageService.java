package pl.service;

import pl.model.Message;
import pl.model.User;

import java.util.Scanner;

public class MessageService {
   Scanner scanner;
   UserService userService;
   ClientService clientService;

   public MessageService(ClientService clientService) {
      this.scanner = new Scanner(System.in);
      this.userService = new UserService();
      this.clientService = clientService;

   }

   public Message getMessageFromUserInput(User sender) {
      return new Message(sender.getName(), scanner.nextLine(), getString("Podaj imię osoby do której piszesz"));
   }

   private String getString(String message) {
      System.out.println(message);
      return scanner.nextLine();
   }

   public boolean printMessages(String receiver, User userPrincipal) {
      boolean couldReceiveMessages = true;
      Thread messageThread = new Thread(() -> new MessageThread(clientService, receiver, userPrincipal, couldReceiveMessages).run());
      messageThread.start();
      return couldReceiveMessages;
   }
}
