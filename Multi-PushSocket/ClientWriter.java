import java.util.*;
import java.net.*;
import java.io.*;


public class ClientWriter extends Thread {
   //private String close;
   private PrintWriter toServer;

   public void passParam(PrintWriter tS) {
      toServer = tS;
      //close = str;
   }
   public void run() {
       String msg = "";
       Scanner scanner = new Scanner(System.in);

       while (true) {
          System.out.print(">");
          msg = scanner.nextLine();
          toServer.println(msg);
       }
       //close = "close";
   }
}
