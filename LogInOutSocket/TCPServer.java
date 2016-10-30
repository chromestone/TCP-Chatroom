import java.io.*;
import java.util.*;
import java.net.*;

public class TCPServer {
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static PrintWriter toClient;
    private static BufferedReader fromClient;
    private static FileInputStream fis;
    private static Scanner fileScanner;
    private static BufferedWriter bufferedWriter;
    private static HashMap hshMp;
    private static Cypher myCyph = new Cypher();
    private static String userAcctFileName = "C:\\Documents and Settings\\Test\\My Documents\\_Derek\\Program\\CypherSimpleSocket\\UserPass.txt";
    private static int portNm = 3000;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        try {
           TCPServer.run(); 
         } 
        catch(IOException e) {
           System.err.println("Error in Server " + e.getMessage());
        }
        catch(NoSuchFieldException a) {
           System.err.println("Error" + a.getMessage());
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void run() throws IOException, NoSuchFieldException {
       String close = "";
        System.out.println("Server is waiting for connections...");
        init();
        while (!"close".equals(close)) {
           openStreams();
           if (socket != null) {
              close = processClientRequest();
           }
           closeStreams();
        }
        close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void init() throws IOException {
       portNm = 3000;
       myCyph = new Cypher();

       userAcctFileName = "C:\\Documents and Settings\\Test\\My Documents\\_Derek\\Program\\LogInOutSocket\\UserPass.txt";
       fileScanner = new Scanner(new FileInputStream(userAcctFileName));
       hshMp=new HashMap<String, String>();
       readFile();
       bufferedWriter = new BufferedWriter(new FileWriter(userAcctFileName));

       //load hashMap from the userAcct file
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void close() throws IOException, NoSuchFieldException {
       writeFile();
       fileScanner.close();
       bufferedWriter.close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void openStreams() throws IOException {
        serverSocket = new ServerSocket(portNm);
        socket = serverSocket.accept();
        toClient = new PrintWriter(socket.getOutputStream(),true);
        fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //writer = new FileWriter("C:\\Documents and Settings\\Test\\My Documents\\_Derek\\Program\\CypherSimpleSocket\\UserPass.txt");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void closeStreams() throws IOException {

       fromClient.close();
       toClient.close();
       socket.close();
       serverSocket.close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static String processClientRequest()throws IOException {
        String msg = ""; 
        String line = "";
        String exit = "";
        String close = "";
        String userID = "";
        //String close = "exit";
        do {
           toClient.println("Enter UserID");
           //System.out.println("before fromClient msg is:" + msg);
           msg = fromClient.readLine();
           //System.out.println("after fromClient msg is:" + msg);
           userID = userExists(msg);
           // check if user is already existing
           // if not, call function sign up
           // if yes, call function sign in
           exit = fromClient.readLine();
           if ("admin".equals(userID) && "CloseServer".equals(exit)) {
              toClient.println("ServerRequestExit");
              close = "close";
              break;
           }
           if ("exit".equals(exit)) {
              toClient.println("ServerRequestExit");
              break;
           }

           //System.out.println("Client said: " + msg);
           //toClient.println("You said: " + encryptMsg);
        }
        while (true);
        return close;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static String userExists(String userID) throws IOException {
       if (hshMp.containsKey(userID)) {
          processClientPassword(userID);
       }
       if (hshMp.containsKey(userID) == false) {
          signUp(userID);
       }
       if (hshMp.isEmpty() == true) {
           signUp(userID);
             //System.out.println(msg + " " + userID);
             //System.out.println("isEmpty()");
             //System.out.println(msg + " " + userID);
             //System.out.println("msg != userID");
          }
    return userID; 
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
                System.out.println("[" + userID + "]");
                pssWord = nextLine.substring(n+1);
                decryptPssWord = myCyph.decrypt(pssWord);
                System.out.println("[" + decryptPssWord + "]");
                hshMp.put(userID, decryptPssWord);
                //System.out.println(userID + pssWord);
             }
          }
          //System.out.println("fromClient msg=" + msg);
          //System.out.println("fileScanner nextLine=" + nextLine);
       }
    }
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

    private static void signUp(String userMsg)throws IOException {
        String passMsg = "";
        String close = "exit";
        String decryptPssWord = "";
        toClient.println("Username Not Found, Enter Password For Sign Up");
        do {
           passMsg = fromClient.readLine();
           //System.out.println(passMsg);
           decryptPssWord = myCyph.decrypt(passMsg);
           hshMp.put(userMsg, decryptPssWord);
           toClient.println("Signed Up, exit To Exit, Enter To Log Out");

        }
        while (!"exit".equals(close));
    }


    private static void processClientPassword(String userID)throws IOException {
        String passMsg = "";
        String close = "";
        String decryptPssWord = "";
        String psswd = "";
        toClient.println("Enter Password");
        do {
           passMsg = fromClient.readLine();
           //System.out.println(passMsg);
           decryptPssWord = myCyph.decrypt(passMsg);
           //System.out.println(decryptPssWord);
           psswd = hshMp.get(userID).toString();
           //System.out.println(psswd);
    
               if (psswd.equals(decryptPssWord)) {
                     toClient.println("Logged In, Type Exit To Exit, Enter To Log Out");
                     close = "exit";
                     break;
                  }
                  else {
                     toClient.println("Password Incorrect");
                     close = "tixe";
                  }
           //System.out.println(msg + passMsg);
           //System.out.println(writeThis);
        }
        while (!"exit".equals(close));
    }
}
