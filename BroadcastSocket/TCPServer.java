import java.io.*;
import java.util.*;
import java.net.*;

public class TCPServer {
   private static Queue<Pair> _broadCastQueue;
   private static HashMap _clientRegist;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
       ServerSocket _serverSocket;
       int _portNm = 3000;
       long threadID;
       _broadCastQueue = new LinkedList<Pair>();
       _clientRegist = new HashMap<String, RequestHandler>();
       
       try {
          //creates instance of broadCast thread
          Broadcast broadCast = new Broadcast();
          //passes parameters to broadCast
          broadCast.setParam(_broadCastQueue,_clientRegist);
          //initiates the thread
          broadCast.start();
          //create a server socket
          _serverSocket = new ServerSocket(_portNm);
          System.out.println("Server is waiting for connections...");
          while (true) {
             //server accpets incomming connections
             Socket socket = _serverSocket.accept();
             if (socket != null) {
                //creates instance of thread
                RequestHandler requestHandler = new RequestHandler();
                //passes parameter and also socket
                requestHandler.setParam(socket,_broadCastQueue,_clientRegist);
                //initiates the thread
                requestHandler.start();
                //makes sure the socket is null again before reaccepting connections
                socket = null;
             }
          }
       }
       catch(IOException e) {
          System.err.println("Error in Server " + e.getMessage());
       }
   }
}
