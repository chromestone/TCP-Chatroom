import java.util.*;
import java.io.*;
import java.net.*;
public class SendTimer extends TimerTask {
   private PrintWriter toClient;
   //private String close;

   public void passParam(PrintWriter tC) {
      toClient = tC;
      //close = str;
   }
   public void run() {
      toClient.println("HI");
   }
}
