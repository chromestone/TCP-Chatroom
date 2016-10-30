import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler extends Thread {
   private Socket _socket;
   private PrintWriter _toClient;
   private BufferedReader _fromClient;


   ///////////////////////////////////////////////////////////////////////////////////////////
   public void setParam(Socket sK) throws IOException {
      try {
         _socket = sK;
         _toClient = new PrintWriter(_socket.getOutputStream(),true);
         _fromClient = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
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
   public void run() {
         ServerWriter serverWriter = new ServerWriter();
         ServerReader serverReader = new ServerReader();
         serverWriter.passParam(_toClient);
         serverReader.passParam(_fromClient);
         serverWriter.start();
         serverReader.start();
   }


   ///////////////////////////////////////////////////////////////////////////////////////////

}
