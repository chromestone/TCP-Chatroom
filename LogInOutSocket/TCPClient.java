import java.io.*;
import java.util.*;
import java.net.*;

public class TCPClient{
   private PrintWriter toServer;
   private BufferedReader fromServer;
   private Socket socket;
//constructor
   public TCPClient() throws IOException {
      //System.out.println("Still Working");
       socket = new Socket("localhost",3000);
       toServer = new PrintWriter(socket.getOutputStream(),true);
       fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   }

   public void openStreams() {
      //System.out.println("Still Working");
   }

   public void closeStreams() throws IOException {
       fromServer.close();
       toServer.close();
       socket.close();
   }

   public void run()throws IOException,NoSuchFieldException {
      //System.out.println("Still Working");
       //openStreams();
      java.util.Date date = new java.util.Date();
      int time = (int) date.getTime();
      Cypher myCyph = new Cypher();
       String tmp = fromServer.readLine();
       System.out.println(tmp);
       String tmp2 = "";
       String msg = "";
       String encryptMsg = "";
       Scanner scanner = new Scanner(System.in);
       System.out.print(">");
       msg = scanner.nextLine();
       toServer.println(msg);
       while (true) {
          tmp2 = fromServer.readLine();
          if ("ServerRequestExit".equals(tmp2)) {
             break;
          }
          else {
             System.out.println(tmp2);
             System.out.print(">");
             msg = scanner.nextLine();
          }
          if ("Enter Password".equals(tmp2) || "Username Not Found, Enter Password For Sign Up".equals(tmp2) || "Password Incorrect".equals(tmp2)) {
             encryptMsg = myCyph.encrypt(msg);
             //System.out.println(encryptMsg);
             toServer.println(encryptMsg);
          }
          else {
             toServer.println(msg);
          }
       }
       closeStreams();
   }
   public static void main(String[] args) {
       try {
        TCPClient client = new TCPClient();
        client.run();
        }
       catch(IOException e) {
          System.err.println("Error in Client " + e.getMessage());
          }
       catch (NoSuchFieldException A) { // exception given if not valid char give when prompted "Enter Password"
             System.out.println("Invalid Letter!");
       }
   }
}
