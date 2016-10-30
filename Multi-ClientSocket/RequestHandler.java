import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler extends Thread {
   private Socket _socket;
   private static HashMap _hshMp;
   private PrintWriter _toClient;
   private BufferedReader _fromClient;
   private static Cypher myCyph = new Cypher();

   public void setParam(Socket sK, HashMap hM) {
      try {
      _socket = sK;
      _toClient = new PrintWriter(_socket.getOutputStream(),true);
      _fromClient = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
      _hshMp = hM;
      }
      catch(IOException a) {
         System.err.println("Error in Server " + a.getMessage());
      }
   }
   private String processClientRequest()throws IOException {
        String msg = ""; 
        String line = "";
        String exit = "";
        String close = "";
        String userID = "";
        //String close = "exit";
        do {
           _toClient.println("Enter UserID");
           //System.out.println("before fromClient msg is:" + msg);
           msg = _fromClient.readLine();
           //System.out.println("after fromClient msg is:" + msg);
           userID = userExists(msg);
           // check if user is already existing
           // if not, call function sign up
           // if yes, call function sign in
           exit = _fromClient.readLine();
           if ("admin".equals(userID) && "CloseServer".equals(exit)) {
              _toClient.println("ServerRequestExit");
              close = "close";
              TCPServer.closeServer();
              //System.out.println("?");
              break;
           }
           if ("exit".equals(exit)) {
              _toClient.println("ServerRequestExit");
              close = "close";
              break;
           }

           //System.out.println("Client said: " + msg);
           //toClient.println("You said: " + encryptMsg);
        }
        while (true);
        return close;
    }
   private String userExists(String userID) throws IOException {
       if (_hshMp.containsKey(userID)) {
          processClientPassword(userID);
       }
       if (_hshMp.containsKey(userID) == false) {
          signUp(userID);
       }
       if (_hshMp.isEmpty() == true) {
           signUp(userID);
             //System.out.println(msg + " " + userID);
             //System.out.println("isEmpty()");
             //System.out.println(msg + " " + userID);
             //System.out.println("msg != userID");
          }
    return userID; 
    }
   private void signUp(String userMsg)throws IOException {
        String passMsg = "";
        String close = "exit";
        String decryptPssWord = "";
        _toClient.println("Username Not Found, Enter Password For Sign Up");
        do {
           passMsg = _fromClient.readLine();
           //System.out.println(passMsg);
           decryptPssWord = myCyph.decrypt(passMsg);
           _hshMp.put(userMsg, decryptPssWord);
           _toClient.println("Signed Up, exit To Exit, Enter To Log Out");

        }
        while (!"exit".equals(close));
    }
   private void processClientPassword(String userID)throws IOException {
        String passMsg = "";
        String close = "";
        String decryptPssWord = "";
        String psswd = "";
        _toClient.println("Enter Password");
        do {
           passMsg = _fromClient.readLine();
           //System.out.println(passMsg);
           decryptPssWord = myCyph.decrypt(passMsg);
           //System.out.println(decryptPssWord);
           psswd = _hshMp.get(userID).toString();
           //System.out.println(psswd);
    
               if (psswd.equals(decryptPssWord)) {
                     _toClient.println("Logged In, Type Exit To Exit, Enter To Log Out");
                     close = "exit";
                     break;
                  }
                  else {
                     _toClient.println("Password Incorrect");
                     close = "tixe";
                  }
           //System.out.println(msg + passMsg);
           //System.out.println(writeThis);
        }
        while (!"exit".equals(close));
    }
   public void closeStreams() throws IOException {

      _fromClient.close();
      _toClient.close();
      _socket.close();
   }
   public void run() {
      try {
         String close = "";
          //System.out.println("Server is waiting for connections...");
          while (!"close".equals(close)) {
             if (_socket != null) {
                close = processClientRequest();
             }
             closeStreams();
          }
      }
          catch(IOException e) {
             System.err.println("Error in Server " + e.getMessage());
          }
   }
}
