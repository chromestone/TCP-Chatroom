import java.io.*;
import java.util.*;
import java.net.*;

public class TCPServer {
    private static ServerSocket serverSocket;
    private static FileInputStream fis;
    private static Scanner fileScanner;
    private static BufferedWriter bufferedWriter;
    private static HashMap hshMp;
    private static Cypher myCyph = new Cypher();
    private static String _userAcctFileName = "C:\\Documents and Settings\\Test\\My Documents\\_Derek\\Program\\Multi-ClientSocket\\UserPass.txt";
    private static int _portNm = 3000;
    private static boolean _closeServer;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
       try {
          hshMp=new HashMap<String, String>();
          init();
          serverSocket = new ServerSocket(_portNm);
          _closeServer = true;
          System.out.println("Server Ready");
          while (_closeServer) {
             if (_closeServer == true) {
                System.out.println("true");
             }
             else {
                System.out.println("false1");
             }
           //try {
             Socket socket = serverSocket.accept();
             if (socket != null) {
                //toClient = new PrintWriter(socket.getOutputStream(),true);
                //fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                RequestHandler requestHandler = new RequestHandler();
                requestHandler.setParam(socket,hshMp);
                requestHandler.start();
                socket = null;
             }
              //TCPServer.run();
            //} 
          }
       }
       catch(IOException e) {
       System.err.println("Error in Server " + e.getMessage());
       }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////
   public static void closeServer() {
      try {
      _closeServer = false;
      serverSocket.close();
      bufferedWriter = new BufferedWriter(new FileWriter(_userAcctFileName));
      writeFile();
      fileScanner.close();
      bufferedWriter.close();
      }
      catch(IOException e) {
         System.err.println("Error in Server " + e.getMessage());
      }
      catch(NoSuchFieldException a) {
         System.err.println("Error" + a.getMessage());
      }
      //if (closeServer == false) {
         //System.out.println("false");
      //}
   }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void init() throws IOException {
       //load hashMap from the userAcct file
       fileScanner = new Scanner(new FileInputStream(_userAcctFileName));
       readFile();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void readFile() throws IOException{
       String userID = "";
       String pssWord = "";
       String decryptPssWord = "";
       String nextLine = "";
       int length;
       while (fileScanner.hasNextLine()) {
          nextLine = fileScanner.nextLine();
          for (int n = 0; n < nextLine.length(); n++) {
             if (nextLine.charAt(n) == "\t".charAt(0)) {
                userID = nextLine.substring(0,n);
                //System.out.println("[" + userID + "]");
                pssWord = nextLine.substring(n+1);
                decryptPssWord = myCyph.decrypt(pssWord);
                //System.out.println("[" + decryptPssWord + "]");
                hshMp.put(userID, decryptPssWord);
                //System.out.println(userID + pssWord);
             }
          }
          //System.out.println("fromClient msg=" + msg);
          //System.out.println("fileScanner nextLine=" + nextLine);
       }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void writeFile() throws IOException, NoSuchFieldException {
       String userID = "";
       String psswd = "";
       String encryptPassword = "";
       String tabMsg = "";
       String writeThis = "";
       //Get Map in Set interface to get key and value
       Set s=hshMp.entrySet();

       //Move next key and value of Map by iterator
       Iterator it=s.iterator();

       while(it.hasNext())
       {
           // key=value separator this by Map.Entry to get key and value
           Map.Entry m =(Map.Entry)it.next();

           // getKey is used to get key of Map
           userID=m.getKey().toString();

           // getValue is used to get value of key in Map
           psswd=m.getValue().toString();
           encryptPassword = myCyph.encrypt(psswd);
           tabMsg = userID + "\t";
           writeThis = tabMsg + encryptPassword;
           //System.out.println(userID + pssWord);
           bufferedWriter.write(writeThis);
           bufferedWriter.newLine();
       }
    }
}
