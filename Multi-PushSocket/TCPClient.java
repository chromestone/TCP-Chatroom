import java.io.*;
import java.util.*;
import java.net.*;

public class TCPClient{
   private PrintWriter toServer;
   private BufferedReader fromServer;
   private Socket socket;
   //private static String close = "abc";
//constructor

   public TCPClient() throws IOException {
       socket = new Socket("localhost",3000);
       toServer = new PrintWriter(socket.getOutputStream(),true);
       fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   }
   public void closeStreams() throws IOException {
       fromServer.close();
       toServer.close();
       socket.close();
   }
   public void thread() {
      ClientReader clientReader = new ClientReader();
      ClientWriter clientWriter = new ClientWriter();
      clientReader.passParam(fromServer);
      clientWriter.passParam(toServer);
      clientReader.start();
      clientWriter.start();
   }

   public static void main(String[] args) {
       try {
          TCPClient client = new TCPClient();
          client.thread();

          //while (!"close".equals(close)) {
             //if ("close".equals(close)) {
                //client.closeStreams();
             //}
          //}

       }
       catch(IOException e) {
          System.err.println("Error in Client " + e.getMessage());
       }
   }
}
