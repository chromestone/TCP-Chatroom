import java.util.*;
import java.net.*;
import java.io.*;

public class ServerReader extends Thread {
   private BufferedReader _fromClient;
   private Queue<Pair> _broadCastQueue;
   private String _userName;
   private boolean privChat;
   private RequestHandler _requestHandler;

   ////////////////////////////////////////////////////////////////////////////////////////////
   public void setParams(BufferedReader fC, Queue qE) {
      _fromClient = fC;
      _broadCastQueue = qE;
   }

   ////////////////////////////////////////////////////////////////////////////////////////////
   public String getUserName() {
      return _userName;
   }
   ////////////////////////////////////////////////////////////////////////////////////////////
   public void run() {
      try {
         _userName = _fromClient.readLine();
         privChat = false;
         boolean talk;
         String privName;
         //used to make sure that the queue has space
         boolean queueSpace;
         String tmp;
         while (true) {
            tmp = _fromClient.readLine();
            // print out on client console
            System.out.println(tmp);
            //use do loop to attempt adding to queue
            do {
               //adds a message to the queue along with identity
               queueSpace = _broadCastQueue.offer(new Pair(_userName, tmp));
            }
            //if attempt failed, try again
            while (queueSpace = false); 
         }
      }
      catch (IOException e){
         System.out.println("Error in ServerReader " + e.getMessage());
      }
   }
}
