import java.io.*;
import java.util.*;
import java.net.*;

public class TCPServer {

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
       ServerSocket _serverSocket;
       int _portNm = 3000;
       
       try {
          //create a server socket
          _serverSocket = new ServerSocket(_portNm);
          System.out.println("Server is waiting for connections...");
          while (true) {
             //server accpets incomming connections
             Socket socket = _serverSocket.accept();
             if (socket != null) {
                RequestHandler requestHandler = new RequestHandler();
                requestHandler.setParam(socket);
                requestHandler.start();
                socket = null;
             }
          }
       }
       catch(IOException e) {
          System.err.println("Error in Server " + e.getMessage());
       }
   }
}
