package pl;

import java.io.IOException;

public class Test {

   public static void main(String[] args) throws IOException {
      Test test = new Test();
      test.givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect();
   }

   public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
      GreetServer greetServer = new GreetServer();
      new Thread(()-> {
         try {
            greetServer.start(6666);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }).start();
      GreetClient client = new GreetClient();
      client.startConnection("127.0.0.1", 6666);
      String response = client.sendMessage("hello server");
      System.out.println(response);
      assert "hello client".equals(response);
   }
}
