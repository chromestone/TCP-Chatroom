import java.util.*;
import java.net.*;
import java.io.*;
public class ClientReader extends Thread {
   //private String close;
   private BufferedReader fromServer;
   public void passParam(BufferedReader fS) {
      //close = str;
      fromServer = fS;
   }
   public void run() {
      try {
         String tmp;
         while (true) {
            tmp = fromServer.readLine();
            System.out.println(" Server:" + tmp);

            //if ("exit".equals(tmp)) {
               //break;
            //}
            //System.out.println(close);

         }
      }
      catch (IOException e){
         System.out.println("Error in Client " + e.getMessage());
      }
   }
}
