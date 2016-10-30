import java.util.*;
import java.net.*;
import java.io.*;



public class ServerWriter extends Thread {
   private PrintWriter toClient;
   //private String close;


   /////////////////////////////////////////////////////////////////////
   public void passParam(PrintWriter tC) {
      toClient = tC;
      //close = str;
   }

   /////////////////////////////////////////////////////////////////////
   public void run() {
      Timer t = new Timer();
      SendTimer sTm = new SendTimer();
      sTm.passParam(toClient);
      t.scheduleAtFixedRate(sTm,0,15000);

      //while (!"close".equals(close) || !"close!".equals(close)) {
         //if ("close".equals(close) || "close!".equals(close)) {
            //toClient.println("exit");
            //t.cancel();
         //}
      //}

   }
}
