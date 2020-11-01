package pl.model;

import java.io.Serializable;

public enum Action implements Serializable {
   REGISTER("Register", true, true),
   LOGIN("Log in", true, true),
   SEND_MESSAGE("Send message to friend", false, false),
   SEND_FILE("Send file to friend", false, false),
   USER_LIST("User list", false, true),
   LOGOUT("Log out", false, true),
   GET_MESSAGES("Get messaeges", false, false),
   EXIT("Exit", true, true);

   private String description;
   private boolean availableAsGuest;
   private boolean showOnMainLoop;

   public String getDescription() {
      return description;
   }

   public boolean isAvailableAsGuest() {
      return availableAsGuest;
   }

   public boolean isShowOnMainLoop() {
      return showOnMainLoop;
   }

   Action(String description, boolean availableAsGuest, boolean showOnMainLoop) {
      this.description = description;
      this.availableAsGuest = availableAsGuest;
      this.showOnMainLoop = showOnMainLoop;
   }
}
