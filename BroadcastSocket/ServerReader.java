import java.util.*;
import java.net.*;
import java.io.*;

public class ServerReader extends Thread {
   private BufferedReader _fromClient;
   private static Queue<Pair> _broadCastQueue;
   private String _userName;
   private static HashMap _clientRegist;
   private RequestHandler _requestHandler;

   ////////////////////////////////////////////////////////////////////////////////////////////
   public void passParam(BufferedReader fC,Queue qE,String str,HashMap hM) {
      _fromClient = fC;
      _broadCastQueue = qE;
      _userName = str;
      _clientRegist = hM;
   }

   ////////////////////////////////////////////////////////////////////////////////////////////
   public void run() {
      try {
         //used to make sure that the queue has space
         boolean queueSpace;
         String tmp;
         while (true) {
            //tmp = _fromClient.readLine();
            tmp = _fromClient.readLine(); 
            // print out on client console
            //System.out.println(tmp);
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
