package uk.ac.ucl.bag;
import com.google.gson.Gson;
//import com.google.gson.*;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;

/**
 * This class implements methods common to all concrete bag implementations
 * but does not represent a complete bag implementation.<br />
 *
 * New bag objects are created using a BagFactory, which can be configured in the application
 * setup to select which bag implementation is to be used.
 */
import java.util.Iterator;

public abstract class AbstractBag<T extends Comparable> implements Bag<T> {
  public Gson gson = new Gson();

  public Bag<T> readIt(Class<T> elementType) throws IOException, BagException {
    T testing;
    Bag<T> result = BagFactory.getInstance().getBag();
    Reader reader = new FileReader("file-output.txt");

    int numCharsRead, forOcc, i = 1;
    char[] charArray = new char[100];
    charArray[99] = '?';
    while (((numCharsRead = reader.read(charArray)) > 0) && (charArray[i] != '?')) {
      String[] values = new String[10];
      int[] occurrences = new int[10];
      String val, occ;
      int j = 0;
        while ((charArray[i] != ']') && (charArray[i] != '?')) {
          val = "";
          occ = "";
          while ((charArray[i] != ':') && (charArray[i] != '?')) {
            if (Character.isLetter(charArray[i]) || Character.isDigit(charArray[i])) {
              val = val + charArray[i];
              System.out.println(val);
            }
            i++;
          }
          values[j] = val;
          while ((charArray[i] != ',') && i < numCharsRead){
            if (Character.isDigit(charArray[i])) {
              occ = occ + charArray[i];
              System.out.println(occ);
            }
            i++;
          }
          if (!occ.equals("")) {
            forOcc = Integer.parseInt(occ);
            occurrences[j] = forOcc;
          }
          testing = gson.fromJson(val, elementType);
          result.addWithOccurrences(testing, occurrences[j]);
          j++;
        }
      }
    return result;
  }

  public void writeIt() throws IOException {
    Writer writer = new FileWriter("file-output.txt");
    writer.write(this.toString());
    writer.close();
  }

  public Bag<T> createMergedAllOccurrences(Bag<T> b) throws BagException {
    Bag<T> result = BagFactory.getInstance().getBag();
    for (T value : this) {
      result.addWithOccurrences(value, this.countOf(value));
    }
    for (T value : b) {
      result.addWithOccurrences(value, b.countOf(value));
    }
    return result;
  }

  public Bag<T> createMergedAllUnique(Bag<T> b) throws BagException {
    Bag<T> result = BagFactory.getInstance().getBag();
    for (T value : this) {
      if (!result.contains(value)) result.add(value);
    }
    for (T value : b) {
      if (!result.contains(value)) result.add(value);
    }
    return result;
  }

  public Bag<T> subtract(Bag<T> b) throws BagException{
    Bag<T> result = BagFactory.getInstance().getBag();
    for ( T value : this) {
      if (!b.contains(value)) {
          result.addWithOccurrences(value, this.countOf(value));
      }
    }
    return result;
  }

  public String toString() {
    String s = "[";
    for (T value : this) {
      String getValue = String.valueOf(value);
      String getOccurrences = String.valueOf(countOf(value));
      s = s + getValue + ": " + getOccurrences + ", ";
    }
    s = s.substring(0, s.length() - 2);
    s = s + "]";
    return s;
  }

  public void removeAllCopies() {
    for (T value : this) {
      while (this.contains(value) && countOf(value) > 1) {
        this.remove(value);
      }
    }
  }
}
