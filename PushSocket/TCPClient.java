import java.io.*;
import java.util.*;
import java.net.*;

public class TCPClient{
   //private PrintWriter toServer;
   private BufferedReader fromServer;
   private Socket socket;
//constructor
   public TCPClient() throws IOException {
      //System.out.println("Still Working");
       socket = new Socket("localhost",3000);
       //toServer = new PrintWriter(socket.getOutputStream(),true);
       fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   }

   public void openStreams() {
      //System.out.println("Still Working");
   }

   public void closeStreams() throws IOException {
       fromServer.close();
       //toServer.close();
       socket.close();
   }

   public void run()throws IOException { //,NoSuchFieldException {
      //System.out.println("Still Working");
       //openStreams();
       //String tmp = fromServer.readLine();
       //System.out.println(tmp);
       String tmp2 = "";
       String msg = "";
       Scanner scanner = new Scanner(System.in);
       //System.out.print(">");
       //msg = scanner.nextLine();
       //toServer.println(msg);
       System.out.println("Client Run Before Loop");
       while (!"abc".equals(tmp2)) {
          tmp2 = fromServer.readLine();
          //if ("ServerRequestExit".equals(tmp2)) {
             //break;
          //}
          //else {
          if (tmp2 != null ) {
             System.out.println("Client readLine=" + tmp2);
          }
             System.out.print(">");
             msg = scanner.nextLine();
          //}
       }
       System.out.println("Client Run Complete");
       closeStreams();
   }
   public static void main(String[] args) {
       try {
        TCPClient client = new TCPClient() {};
        client.run();
        }
       catch(IOException e) {
          System.err.println("Error in Client " + e.getMessage());
          }
       //catch (NoSuchFieldException A) { // exception given if not valid char give when prompted "Enter Password"
             //System.out.println("Invalid Letter!");
       //}
   }
}
