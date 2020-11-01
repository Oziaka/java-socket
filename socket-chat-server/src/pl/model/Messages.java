package pl.model;

import java.io.Serializable;
import java.util.List;

public class Messages implements Serializable {

   private String sender;
   private String receiver;
   private List<String> textMessages;


   public Messages(String sender, String receiverUserName, List<String> textMessages) {
      this.sender = sender;
      this.receiver = receiverUserName;
      this.textMessages = textMessages;
   }

}
