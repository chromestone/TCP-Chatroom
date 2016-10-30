import java.util.*;
import java.net.*;
import java.io.*;

public class SocketHandler {
private PrintWriter toClient;
private Socket socket;
private HashMap hshMp;
private int socketID;
   public void createIO(Socket sk, HashMap hM, int i) {
      socket = sk;
      hshMp = hM;
      socketID = i;
      try {
         toClient = new PrintWriter(socket.getOutputStream(),true);
      }
      catch (IOException e) {
         System.err.println("Error in Server " + e.getMessage());
      }
   }
   public void toClient(String msg) {
      toClient.println(msg);
      System.out.println(socket.isConnected());
      if (socket.isConnected() == false) {
         toClient.close();
         hshMp.remove(socketID);
      }
   }
}
