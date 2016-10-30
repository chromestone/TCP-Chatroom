import java.util.*;
import java.io.*;
import java.net.*;
public class SendTimer extends TimerTask {
   private HashMap hshMp;

   public void setMap(HashMap hM) {
      hshMp = hM;
   }
   public void run() {
      int size = hshMp.size();
      SocketHandler socketHandler;
      if (hshMp.isEmpty() == false) {
         //boolean stateOfSocket;
         Socket socket = null;
         System.out.println("hash size= " + size);
         //System.out.println("HI");
         //Get Map in Set interface to get key and value
         Set s=hshMp.entrySet();
   
         //Move next key and value of Map by iterator
         Iterator it=s.iterator();
   
            for (int i = 0; i < size; i++) 
            {
               System.out.println("Enter While");
                // key=value separator this by Map.Entry to get key and value
                Map.Entry m =(Map.Entry)it.next();
      
                // getKey is used to get key of Map
                //String key=m.getKey().toString();
      
                // getValue is used to get value of key in Map
                socketHandler = (SocketHandler)m.getValue();

                //stateOfSocket = socket.isClosed();
                //System.out.println("Socket Close1 " + stateOfSocket);

                socketHandler.toClient("HI!");
                //socket = null;
      
                //System.out.println("Key :"+key+"  Value :"+value);
            }
            //stateOfSocket = socket.isClosed();
            //System.out.println("Socket Close2 " + stateOfSocket);
         //stateOfSocket = socket.isClosed();
         //System.out.println("Socket Close3 " + stateOfSocket);
      }
   }
}
