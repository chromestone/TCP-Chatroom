import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler extends Thread {
   private Socket _socket;
   private PrintWriter _toClient;
   private BufferedReader _fromClient;
   private ServerReader _serverReader;
   //private ServerReader serverReader;


   //////////////////////////////////////////////////////////////////////////////////////////
   public void setParam(Socket sK, Queue<Pair> qu) throws IOException {
      try {
         _socket = sK;
         _toClient = new PrintWriter(_socket.getOutputStream(),true);
         _fromClient = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
         _serverReader = new ServerReader();
         _serverReader.setParams(_fromClient, qu);
        
      }
      catch(IOException a) {
         System.err.println("Error when set Params:" + a.getMessage());
         throw a;
      }
   }

   ///////////////////////////////////////////////////////////////////////////////////////////
   public void closeStreams() throws IOException {
      _fromClient.close();
      _toClient.close();
      _socket.close();
   }


   ///////////////////////////////////////////////////////////////////////////////////////////
   public String getUserName() {
      return _serverReader.getUserName();
   }
   ///////////////////////////////////////////////////////////////////////////////////////////
   public void toClient(String userName,String serverMsg) {
      //used by Broadcast to send client a message
      _toClient.println(userName + " said: " +serverMsg);
   }
   //////////////////////////////////////////////////////////////////////////////////////////
   public void run() {
   //creates instance of thread that handles client messages
   _toClient.println("Enter UserName");
   //initiates the thread
   _serverReader.start();
   //ServerWriter serverWriter = new ServerWriter();
   //serverWriter.passParam(_toClient);
   //serverWriter.start();
   }


   ///////////////////////////////////////////////////////////////////////////////////////////

}
