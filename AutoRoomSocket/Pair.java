import java.util.*;
import java.io.*;

public class Pair {

   private Object _first;
   private Object _second;

   public Pair(Object first, Object second) {
      _first = first;
      _second = second;
   }
   public Object getKey() {
      return _first;
   }
   public Object getValue() {
      return _second;
   }
}
