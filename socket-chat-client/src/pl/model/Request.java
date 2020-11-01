package pl.model;

import java.io.Serializable;

public class Request implements Serializable {
   public Action getAction() {
      return action;
   }

   public Object getObject() {
      return object;
   }

   public User getUser() {
      return user;
   }

   private Action action;
   private Object object;
   private User user;

   public Request(Action action, Object object, User user) {
      this.action = action;
      this.object = object;
      this.user = user;
   }
}
