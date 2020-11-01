package pl.controller;

import pl.database.repository.MessageRepository;
import pl.database.repository.UserRepository;
import pl.model.Message;
import pl.model.Request;
import pl.model.Response;
import pl.model.User;

import java.sql.Statement;
import java.util.List;

public class ActionController {

   private UserRepository userRepository;
   private MessageRepository messageRepository;

   public ActionController(Statement statement) {
      this.messageRepository = new MessageRepository(statement);
      this.userRepository = new UserRepository(statement);
   }

   public Response doAction(Request request) {
      switch (request.getAction()) {
         case REGISTER: {
            User user = (User) request.getObject();
            if (user.getName().equals("Guest"))
               return Response.failed("User can not have this name");
            else if (userRepository.isuserExist(user))
               return Response.failed("User is exist");
            else {
               userRepository.save(user);
               return Response.success(user);
            }
         }
         case LOGIN: {
            User user = (User) request.getObject();
            if (userRepository.isCorrectUser(user))
               return Response.success(user);
            else
               return Response.failed("Bad username or password or both");
         }
         case SEND_MESSAGE: {
            User user = request.getUser();
            if (userRepository.isCorrectUser(user)) {
               Message message = (Message) request.getObject();
               Long senderId = userRepository.get(user).getId();
               Long receiverId = userRepository.get(new User(message.getReceiver())).getId();
               String text = message.getText();
               messageRepository.save(senderId, receiverId, text);
               return Response.success(null);
            } else {
               return Response.failed("Invalid principal");
            }
         }
         case SEND_FILE:
            break;
         case USER_LIST:
            User user = request.getUser();
            if (userRepository.isCorrectUser(user))
               return Response.success(userRepository.getUsers());
            else
               return Response.failed("Invalid principal");
         case LOGOUT:
            break;
         case GET_MESSAGES: {
            User sender = request.getUser();
            if (userRepository.isCorrectUser(sender)) {
               String receiver = ((User) request.getObject()).getName();
               Long senderId = userRepository.get(sender).getId();
               Long receiverId = userRepository.get(new User(receiver)).getId();
               List<Message> messages = messageRepository.getAll(senderId, receiverId);
               return Response.success(messages);
            } else
               return Response.failed("Bad username or password or both");
         }
      }
      return Response.failed("Unknown action");
   }
}
