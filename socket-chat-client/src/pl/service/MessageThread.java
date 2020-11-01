package pl.service;

import pl.model.*;

import java.util.List;

public class MessageThread implements Runnable {
   private ClientService clientService;
   private String receiver;
   private User sender;
   private boolean couldReceiverMessage;


   public MessageThread(ClientService clientService, String receiver, User sender, boolean couldReceiverMessage) {
      this.clientService = clientService;
      this.receiver = receiver;
      this.sender = sender;
      this.couldReceiverMessage = couldReceiverMessage;

   }

   public void setReceiverMessage() {
      this.couldReceiverMessage = false;
   }

   @Override
   public void run() {
      int messageCount = 0;
      while (couldReceiverMessage) {
         Response response = clientService.sendAsUser(Action.GET_MESSAGES, new User(receiver, null), sender);
         if (response.getSatatus().equals(Status.SUCCESS)) {
            List<Message> newMessages = (List<Message>) response.getObjects();
            for (int i = messageCount; newMessages != null && i < newMessages.size(); i++) {
               Message currentMessage = newMessages.get(i);
               System.out.println(currentMessage.getSender() + ": " + currentMessage.getText());
            }
            if (newMessages != null)
               messageCount = newMessages.size();
         }
         try {
            Thread.sleep(3000);
         } catch (InterruptedException e) {
         }
      }
   }
}
