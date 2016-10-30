import java.io.*;
import java.util.*;
import java.net.*;

public class TCPClient{
   private PrintWriter _toServer;
   private BufferedReader _fromServer;
   private Socket _socket;

//constructor
   public TCPClient() throws IOException {
       _socket = new Socket("localhost",3000);
       _toServer = new PrintWriter(_socket.getOutputStream(),true);
       _fromServer = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
   }
   public void closeStreams() throws IOException {
       _fromServer.close();
       _toServer.close();
       _socket.close();
   }
   public void thread() {
      //creates instance of client's reader
      ClientReader clientReader = new ClientReader();
      //creates instance of client's writer
      ClientWriter clientWriter = new ClientWriter();
      //passes parameters to the threads
      clientReader.passParam(_fromServer);
      clientWriter.passParam(_toServer);
      //starts the threads
      clientReader.start();
      clientWriter.start();
   }

   public static void main(String[] args) {
       try {
          TCPClient client = new TCPClient();
          client.thread();
       }
       catch(IOException e) {
          System.err.println("Error in Client " + e.getMessage());
       }
   }
}
