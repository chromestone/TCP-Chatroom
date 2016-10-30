import java.util.*;
import java.net.*;
import java.io.*;

public class ServerReader extends Thread {
   private BufferedReader _fromClient;
   private static Queue<Pair> _broadCastQueue;
   private String _userName;
   private static HashMap _clientRegist;
   private boolean privChat;
   private RequestHandler _requestHandler;

   ////////////////////////////////////////////////////////////////////////////////////////////
   // project: Private Chat
   ////////////////////////////////////////////////////////////////////////////////////////////

   private void privChat(RequestHandler requestHandler, String str) {//throws IOException {
      //String tmp = "";
      //while (!"[Public]".equals(tmp)) {
         //try {
         //tmp = fromClient();
         requestHandler.toClient(_userName, str);
         //}
         //catch (IOException e) {
            //System.err.println("Error in privChat:" + e.getMessage());
            //throw e;
         //}
      //}
   }

   ////////////////////////////////////////////////////////////////////////////////////////////
   public void passParam(BufferedReader fC,Queue qE,String str,HashMap hM) {
      _fromClient = fC;
      _broadCastQueue = qE;
      _userName = str;
      _clientRegist = hM;
   }

   ////////////////////////////////////////////////////////////////////////////////////////////
   private void booleanSetter(RequestHandler rH) {
      System.out.println("hi1");
      privChat = true;
      _requestHandler = rH;
   }
   ////////////////////////////////////////////////////////////////////////////////////////////
   public synchronized String fromClient() throws IOException {
      try {
      String msg = _fromClient.readLine();
      return msg;
      }
      catch (IOException e) {
         System.err.println("Error in fromClient:" + e.getMessage());
         throw e;
      }
   }
   ////////////////////////////////////////////////////////////////////////////////////////////
   public void run() {
      try {
         //privChat = false;
         //boolean talk;
         String privName;
         //used to make sure that the queue has space
         boolean queueSpace;
         String tmp;
         String msg;
         while (true) {
            tmp = fromClient();
            // print out on server console
            System.out.println(tmp);
            //use do loop to attempt adding to queue
            if (tmp.length() > 4) {
               if ("[".equals(tmp.substring(0,0))) {
                  int x;
                  for (x = 0; x < tmp.length(); x++) {
                     if ("]".equals(tmp.charAt(x))) {
                        break;
                     }
                  }
                  privName = tmp.substring(1, x-1); 
                  if (_clientRegist.containsKey(privName)) {
                     // find target client request handler
                     RequestHandler requestHandler = (RequestHandler)_clientRegist.get(privName);
                     msg = tmp.substring(x+1, tmp.length());
                     //privChat(requestHandler);
                     //talk = requestHandler.privMsg(_userName);
                     //if (talk == true) {
                        //System.out.println("it's true");
                        privChat(requestHandler, msg);
                     //}
                  }
               }
            }
            //if (privChat == true) {
               //System.out.println("it's true 2");
               //privChat(_requestHandler);
            //}
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
