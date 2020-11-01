package pl;

import pl.model.Action;
import pl.model.Message;
import pl.model.Response;
import pl.model.User;
import pl.service.ClientService;
import pl.service.MessageService;
import pl.service.UserService;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static pl.model.Action.*;
import static pl.model.Status.SUCCESS;

public class ActionController {
   private ClientService clientService;
   private UserService userService;
   private MessageService messageService;
   private User userPrincipal;


   public ActionController(ClientService clientService) {
      this.clientService = clientService;
      this.userService = new UserService();
      this.messageService = new MessageService(clientService);
      this.userPrincipal = null;
   }

   public Action chooseAction() {
      List<Action> actions = Arrays.stream(values()).filter(a -> (a.isAvailableAsGuest() && userPrincipal == null) || a.isShowOnMainLoop() && (!a.isAvailableAsGuest() && userPrincipal != null)).collect(Collectors.toList());
      for (int i = 0; i < actions.size(); i++) {
         System.out.println((i + 1) + ". " + actions.get(i).getDescription());
      }
      return actions.get(getIntegerFromUser(actions.size()));
   }


   private int getIntegerFromUser(int max) {
      Scanner scanner = new Scanner(System.in);
      while (true) {
         try {
            int actionNumber = scanner.nextInt();
            if (actionNumber > max)
               continue;
            return (actionNumber - 1);
         } catch (InputMismatchException ignored) {
         }
      }
   }

   public void doAction(Action action) {
      switch (action) {
         case REGISTER: {
            User user = userService.getUserFromUserInput();
            Response response = clientService.sendAsGuest(REGISTER, user);
            if (response.getSatatus().equals(SUCCESS))
               userPrincipal = (User) response.getObjects();
            else
               System.err.println(response.getError());
            break;
         }
         case LOGIN: {
            User user = userService.getUserFromUserInput();
            Response response = clientService.sendAsGuest(LOGIN, user);
            if (response.getSatatus().equals(SUCCESS))
               userPrincipal = (User) response.getObjects();
            else
               System.err.println(response.getError());
            break;
         }
         case USER_LIST: {
            Response response = clientService.sendAsUser(USER_LIST, null, userPrincipal);
            if (response.getSatatus().equals(SUCCESS)) {
               User receiver = chooseReceiver((List<User>) response.getObjects());
               boolean couldReceiveMessages = messageService.printMessages(receiver.getName(), userPrincipal);
               System.out.println("To finish enter \"exit\"");
               Scanner scanner = new Scanner(System.in);
               for (String message = scanner.nextLine(); !message.equals("exit"); message = scanner.nextLine())
                  clientService.sendAsUser(SEND_MESSAGE, new Message(userPrincipal.getName(), receiver.getName(), message), userPrincipal);
               couldReceiveMessages = false;
            } else
               System.out.println(response.getError());
         }
         break;
         case LOGOUT: {
            userPrincipal = null;
            break;
         }
         case EXIT:
            System.exit(0);
            break;
      }
   }

   public User chooseReceiver(List<User> users) {
      users = users.stream().filter(u -> !u.getName().equals(userPrincipal.getName())).collect(Collectors.toList());
      for (int i = 0; i < users.size(); i++)
         System.out.println((i + 1) + " " + users.get(i).getName());
      return users.get(getIntegerFromUser(users.size()));
   }
}
