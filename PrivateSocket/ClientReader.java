import java.util.*;
import java.net.*;
import java.io.*;
public class ClientReader extends Thread {
   private BufferedReader _fromServer;

   public void passParam(BufferedReader fS) {
      _fromServer = fS;
   }
   public void run() {
      try {
         String tmp;
         while (true) {
            tmp = _fromServer.readLine();
            System.out.println(tmp);
         }
      }
      catch (IOException e){
         System.out.println("Error in Client " + e.getMessage());
      }
   }
}
