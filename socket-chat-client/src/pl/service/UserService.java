package pl.service;

import pl.model.User;

import java.util.Scanner;

public class UserService {
   Scanner scanner;

   public UserService() {
      this.scanner = new Scanner(System.in);
   }

   public User getUserFromUserInput() {
      return new User(getString("Podaj imię"), getString("Podaj hasło"));
   }

   private String getString(String message) {
      System.out.println(message);
      return scanner.next();
   }
}
