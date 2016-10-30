import java.util.*;
import java.net.*;
import java.io.*;

public class ServerReader extends Thread {
   private BufferedReader _fromClient;

   ////////////////////////////////////////////////////////////////////////////////////////////
   public void passParam(BufferedReader fC) {
      _fromClient = fC;
   }

   ////////////////////////////////////////////////////////////////////////////////////////////
   public void run() {
      try {
         String tmp;
         while (true) {
            tmp = _fromClient.readLine();
            // print out on client console
            System.out.println(tmp);
         }
      }
      catch (IOException e){
         System.out.println("Error in Client " + e.getMessage());
      }
   }
}
