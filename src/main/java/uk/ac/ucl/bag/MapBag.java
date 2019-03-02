package uk.ac.ucl.bag;

import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;


public class MapBag<T extends Comparable> extends AbstractBag<T> {
    private int maxSize;
    private HashMap<T, Integer> contents;

    public MapBag() throws BagException
    {
        this(MAX_SIZE);
    }

    public MapBag(int maxSize) throws BagException {
        if (maxSize > MAX_SIZE)
        {
            throw new BagException("Attempting to create a Bag with size greater than maximum");
        }
        if (maxSize < 1)
        {
            throw new BagException("Attempting to create a Bag with size less than 1");
        }
        this.maxSize = maxSize;
        this.contents = new HashMap<>();
    }

    public void add(T value) throws BagException {
        if (contents.size() >= maxSize) {
            throw new BagException("Bag is full");
        } else {
            contents.put(value, countOf(value) + 1);
        }
//        if (contents.containsKey(value) == true ) {
//            contents.put(value, contents.get(value) +1);
//            return;
//        }
//        if ( contents.size() < MAX_SIZE) {
//            contents.put(value, 1);
//        }
//        else {
//            throw new BagException("Bag is full");
//        }
    }

    public void addWithOccurrences(T value, int occurrences) throws BagException {
//        for (T key : contents.keySet()) {
//            if (key.compareTo(value) == 0 && occurrences == 0) {
//                add(value);
//            }
//        }
//        if (size() < maxSize) {
//            contents.put(value, countOf(value) + occurrences);
//        }
//        else {
//            throw new BagException("Bag is full");
//        }
        for (int i = 0 ; i < occurrences ; i++)
        {
            add(value);
        }
    }

    public boolean contains(T value) {
//        for ( T key : contents.keySet()) {
//            if (key.compareTo(value) == 0) {
//                return true;
//            }
//        }
//        return false;
        return contents.containsKey(value);
    }

    public int countOf(T value) {
        int x = 0;
        for ( T key : contents.keySet()) {
            if (key.compareTo(value) == 0) {
                x = contents.get(key);
            }
        }
        return x;
    }

    public void remove(T value) {
        for ( T key : contents.keySet()) {
            if (key.compareTo(value) == 0) {
                remove(value);
            }
        }
    }

    public boolean isEmpty() {
        return(contents.size() == 0);
    }

    public int size() {
        return(contents.size());
    }

    private class MapBagUniqueIterator implements Iterator<T>
    {
        private int index = 0;
        private ArrayList<T> listOfKeys= new ArrayList<T>(contents.keySet());
        public boolean hasNext()
        {
            if (index < listOfKeys.size()) return true;
            return false;
        }

        public T next() { return listOfKeys.get(index++); }
    }

    public Iterator<T> iterator() {
        return new MapBagUniqueIterator();
    }

    private class MapBagIterator implements Iterator<T>
    {
        private int index = 0;
        private int count = 0;
        private ArrayList<T> listOfKeys= new ArrayList<T>(contents.keySet());

        public boolean hasNext()
        {
            if (index < listOfKeys.size()) {
                if (count < countOf(listOfKeys.get(index))) return true;
                if ((count == countOf(listOfKeys.get(index))) && ((index + 1) < listOfKeys.size())) return true;
            }
            return false;
        }

        public T next()
        {
            if (count < countOf(listOfKeys.get(index)))
            {
                T value = listOfKeys.get(index);
                count++;
                return value;
            }
            count = 1;
            index++;
            return listOfKeys.get(index);
        }
    }

    public Iterator<T> allOccurrencesIterator() {
        return new MapBagIterator(); }

}
