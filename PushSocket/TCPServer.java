import java.io.*;
import java.util.*;
import java.net.*;

public class TCPServer {
   private static ServerSocket serverSocket;
    private static PrintWriter toClient;
    private static BufferedReader fromClient;
    private static FileInputStream fis;
    private static Scanner fileScanner;
    private static BufferedWriter bufferedWriter;
    private static String userAcctFileName = "C:\\Documents and Settings\\Test\\My Documents\\_Derek\\Program\\CypherSimpleSocket\\UserPass.txt";
    private static int portNm = 3000;
    private static Cypher myCyph = new Cypher();

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        try {
           //TCPServer.run(); 
           serverSocket = new ServerSocket(portNm);
           HashMap hshMp = new HashMap<Integer, SocketHandler>();
           Timer t = new Timer();
           SendTimer sTm = new SendTimer();
           sTm.setMap(hshMp);
           t.scheduleAtFixedRate(sTm,0,5000);
           //while (true) {
           Socket socket = serverSocket.accept();
              System.out.println("Get a new socket");
              SocketHandler socketHandler = new SocketHandler();
              Random rand = new Random();
              int x = rand.nextInt(10001);
              socketHandler.createIO(socket, hshMp, x);
              hshMp.put(x, socketHandler);
              //socket = null;
         //} 
        }
        catch(IOException e) {
           System.err.println("Error in Server1 " + e.getMessage());
        }
        //catch(NoSuchFieldException a) {
           //System.err.println("Error" + a.getMessage());
        //}
    }
    
}
