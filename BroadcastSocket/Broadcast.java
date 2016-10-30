import java.util.*;
import java.net.*;
import java.io.*;

public class Broadcast extends Thread {

   private static Queue<Pair> _broadCastQueue;
   private static HashMap _clientRegist;
  
   public void setParam(Queue<Pair> qE,HashMap hM) {
      _broadCastQueue = qE;
      _clientRegist = hM;
   }
   public void run() {
      RequestHandler requestHandler;
      Pair pair;
      String userName;
      String clientMsg;
      String hshUser;
      // loop for each client's request handler
      while (true) { 
         //makes sure there are clients and messages from client
         while (_clientRegist.isEmpty() == false && _broadCastQueue.peek() != null) {
        //Get Map in Set interface to get key and value
            Set s = _clientRegist.entrySet();
        //Move next key and value of Map by iterator
            Iterator it = s.iterator();
            //gets the information and at the same time removing it from the queue
            pair = (Pair)_broadCastQueue.poll();
            //used later the ensure the send doesn't receive his own message
            userName = (String)pair.getKey();
            //gets the message
            clientMsg = (String)pair.getValue();
            //makes sure there is next in hashtable
            while(it.hasNext())
            {
            // key=value separator this by Map.Entry to get key and value
               Map.Entry m =(Map.Entry)it.next();
            // getKey is used to get username of client 
               hshUser = (String)m.getKey();
               //if the username is equal to the user who said the message, the message is not broadcasted to that user
               if (!hshUser.equals(userName)) {
            // getValue is used to get the thread of key in Map
                  requestHandler = (RequestHandler)m.getValue();
                  // the thread is called and messages the client
                  requestHandler.toClient(userName,clientMsg);
               }
            }
         }
      }
   }
}
