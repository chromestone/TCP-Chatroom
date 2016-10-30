import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler extends Thread {
   private Socket _socket;
   private PrintWriter _toClient;
   private BufferedReader _fromClient;
   private static Queue<Pair> _broadCastQueue;
   private static HashMap _clientRegist;
   private ServerReader _serverReader;
   //private ServerReader serverReader;


   //////////////////////////////////////////////////////////////////////////////////////////
   public void setParam(Socket sK, Queue<Pair> qE, HashMap hM) throws IOException {
      try {
         _socket = sK;
         _toClient = new PrintWriter(_socket.getOutputStream(),true);
         _fromClient = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
         _broadCastQueue = qE;
         _clientRegist = hM;
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
   public void toClient(String userName,String serverMsg) {
      //used by Broadcast to send client a message
      _toClient.println(userName + " said: " +serverMsg);
   }
   //////////////////////////////////////////////////////////////////////////////////////////
   public void run() {
      try {
         //creates instance of thread that handles client messages
         _serverReader = new ServerReader();
         _toClient.println("Enter UserName");
         //sets a identity for messages broadcasted
         String userName = _fromClient.readLine();
         //puts the identity and this object's identity into the hashmap
         _clientRegist.put(userName, this);
         //gives parameters the thread
         _serverReader.passParam(_fromClient,_broadCastQueue,userName,_clientRegist);
         //initiates the thread
         _serverReader.start();
//         hshMp.put(userName,serverWriter);
         //ServerWriter serverWriter = new ServerWriter();
         //serverWriter.passParam(_toClient);
         //serverWriter.start();
      }
      catch (IOException e) {
         System.err.println("Error when request UserName:" + e.getMessage());
      }
   }


   ///////////////////////////////////////////////////////////////////////////////////////////

}
