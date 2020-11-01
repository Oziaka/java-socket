package pl.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

   private Long id;
   private String name;
   private String password;

   public User(Long id, String name, String password) {
      this.id = id;
      this.name = name;
      this.password = password;
   }

   public String getName() {
      return name;
   }

   public String getPassword() {
      return password;
   }

   public User(String name) {
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public User(String name, String password) {
      this.name = name;
      this.password = password;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(name, user.name) &&
         Objects.equals(password, user.password);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name, password);
   }

   @Override
   public String toString() {
      return "User{" +
         "name='" + name + '\'' +
         ", password='" + password + '\'' +
         '}';
   }
}
