import java.io.*;
import java.util.*;
import java.net.*;

public class TCPServer {
   private Queue<Pair> _broadCastQueue;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
       ServerSocket _serverSocket;
       int _portNm = 3000;
       long threadID;
       
       try {
          //create a server socket
          _serverSocket = new ServerSocket(_portNm);
          System.out.println("Server is waiting for connections...");
          while (true) {
             Queue<Pair> _broadCastQueue = new LinkedList<Pair>();

             //create a private room thread
             PrivateRoom privateRoom = new PrivateRoom();
             //passes message queue to private room
             privateRoom.setQueue(_broadCastQueue);



             //server accpets first incomming connection request
             Socket socket = _serverSocket.accept();

             //create the first request handler for first request
             RequestHandler requestHandler1 = new RequestHandler();

             //passes message queue and socket to request handler 
             requestHandler1.setParam(socket, _broadCastQueue);

             //run the first handler
             requestHandler1.start();

             // set request handler for the left chatter in private room
             privateRoom.setLeft(requestHandler1);



                //server accpets second incomming connection request
                socket = _serverSocket.accept();
   
                //create the second request handler for second request
                RequestHandler requestHandler2 = new RequestHandler();
   
                //passes message queue and socket to request handler 
                requestHandler2.setParam(socket, _broadCastQueue);
   
                //run the second handler
                requestHandler2.start();
   
                // set request handler for the left chatter in private room
                privateRoom.setRight(requestHandler2);

             //initiates the thread
             privateRoom.start();
          }
       }
       catch(IOException e) {
          System.err.println("Error in Server " + e.getMessage());
       }
   }
}
