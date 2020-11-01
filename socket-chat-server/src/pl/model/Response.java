package pl.model;

import java.io.Serializable;

public class Response implements Serializable {
   private Status satatus;
   private Object objects;
   private String error;

   public Response(Status satatus, String error) {
      this.satatus = satatus;
      this.error = error;
   }

   public Response(Status satatus, Object objects) {
      this.satatus = satatus;
      this.objects = objects;
   }

   public Status getSatatus() {
      return satatus;
   }

   public void setSatatus(Status satatus) {
      this.satatus = satatus;
   }

   public Object getObjects() {
      return objects;
   }

   public void setObjects(Object objects) {
      this.objects = objects;
   }

   public String getError() {
      return error;
   }

   public void setError(String error) {
      this.error = error;
   }

   public static Response failed(String error){
      return new Response(Status.FAILED, error);
   }
   public static Response success(Object object){
      return new Response(Status.SUCCESS, object);
   }
}
