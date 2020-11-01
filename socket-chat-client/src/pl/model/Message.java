package pl.model;

import java.io.Serializable;

public class Message implements Serializable {

   private String sender;
   private String receiver;
   private String text;


   public String getSender() {
      return sender;
   }

   public String getReceiver() {
      return receiver;
   }

   public String getText() {
      return text;
   }

   public Message(String sender, String receiverUserName, String textMessage) {
      this.sender = sender;
      this.receiver = receiverUserName;
      this.text = textMessage;
   }

}
