package pl.model;

import java.io.Serializable;

public class File implements Serializable {

   private String receiver;
   private String text;

   public String getReceiver() {
      return receiver;
   }

   public File(String receiver, String text) {
      this.receiver = receiver;
      this.text = text;
   }

   public void setReceiver(String receiver) {
      this.receiver = receiver;
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }
}
