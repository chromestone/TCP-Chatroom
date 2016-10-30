import java.util.*;
import java.net.*;
import java.io.*;

public class ClientWriter extends Thread {
   private PrintWriter _toServer;

   public void passParam(PrintWriter tS) {
      _toServer = tS;
   }
   public void run() {
       String msg = "";
       Scanner scanner = new Scanner(System.in);

       while (true) {
          msg = scanner.nextLine();
          _toServer.println(msg);
       }
   }
}
