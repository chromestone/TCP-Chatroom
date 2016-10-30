import java.util.*;
import java.net.*;
import java.io.*;

public class PrivateRoom extends Thread {

   private Queue<Pair> _broadCastQueue;
   private RequestHandler leftPerson;
   private RequestHandler rightPerson;
  
   public void setQueue(Queue<Pair> qE) {
      _broadCastQueue = qE;
   }
   public void setLeft(RequestHandler rH) {
      leftPerson = rH;
   }
   public void setRight(RequestHandler rH) {
      rightPerson = rH;
   }
   public void run() {
      RequestHandler requestHandler;
      Pair pair;
      String userName;
      String clientMsg;
      // loop for each client's request handler
      while (true) { 
         String leftUser = leftPerson.getUserName();
         String rightUser = rightPerson.getUserName();
         //makes sure there are clients and messages from client
         while (_broadCastQueue.peek() != null) {
            //gets the information and at the same time removing it from the queue
            pair = (Pair)_broadCastQueue.poll();
            //used later the ensure the send doesn't receive his own message
            userName = (String)pair.getKey();
            //gets the message
            clientMsg = (String)pair.getValue();
            // the thread is called and messages the client
            if (leftUser.equals(userName)) {
               rightPerson.toClient(userName,clientMsg);
            }
            else if (leftUser.equals(userName)) {
               leftPerson.toClient(userName,clientMsg);
            }
         }
      }
   }
}
