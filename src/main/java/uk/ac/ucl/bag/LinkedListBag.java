package uk.ac.ucl.bag;

import java.util.Iterator;
import java.util.ArrayList;

public class LinkedListBag<T extends Comparable> extends AbstractBag<T> {
    private Element<T> head = null;
    private Element<T> tail = null;

    private static class Element<E> {
        public E value;
        public int occurrences;
        public Element<E> next;

        public Element(E value, int occurrences, Element<E> next) {
            this.value = value;
            this.occurrences = occurrences;
            this.next = next;
        }
    }

    private int maxSize;

    public LinkedListBag() throws BagException {
        this(MAX_SIZE);
    }

    public LinkedListBag(int maxSize) throws BagException {
        if (maxSize > MAX_SIZE) {
            throw new BagException("Attempting to create a Bag with size greater than maximum");
        }
        if (maxSize < 1) {
            throw new BagException("Attempting to create a Bag with size less than 1");
        }
        this.maxSize = maxSize;
    }

    public void add(T value) throws BagException {
        if (head == null) {
            head = new Element(value, 1, null);
        }
        else if (head.next == null) {
            if (head.value.compareTo(value) == 0) {
                head.occurrences++;
                return;
            }
            tail = new Element(value, 1, null);
            head.next = tail;
        }
        else if (head.next != null) {
            tail = head;
            while (tail.next != null) {
                if (tail.value.compareTo(value) == 0) {
                    tail.occurrences++;
                    return;
                }
                tail = tail.next;
            }
            if (tail.value.compareTo(value) == 0) {
                tail.occurrences++;
                return;
            }
            else {
                tail.next = new Element(value, 1, null);
            }
        }
        else {
            throw new BagException("Bag is full");
        }
    }

    public void addWithOccurrences(T value, int occurrences) throws BagException {
        for (int i = 0; i < occurrences; i++) {
            add(value);
        }
    }
    public boolean contains(T value) {
        tail = head;
        while (tail != null) {
            if (tail.value.compareTo(value) == 0) {
                return true;
            }
            tail = tail.next;
        }
        return false;
    }
    public int countOf(T value) {
        tail = head;
        while (tail != null) {
            if (tail.value.compareTo(value) == 0 ) {
                return tail.occurrences;
            }
            tail = tail.next;
        }
        return 0;
    }
    public void remove(T value) {
        tail = head;
        while (tail != null ) {
            if (tail.value.compareTo(value) == 0 ) {
                head = tail.next;
            }
            else if (tail.next.value.compareTo(value) == 0 ) {
                tail.next = tail.next.next;
            }
        }
    }
    public boolean isEmpty()
    {
        if (head == null)  return true;
        return false;
    }
    public int size() {
        int i = 0;
        tail = head;
        while (tail != null) {
            i++;
            tail = tail.next;
        }
        return i;
    }
//    private ArrayList<T> storeKeys() {
//        tail = head;
//        while (tail != null) {
//            listOfKeys.add(tail.value);
//            tail = tail.next;
//        }
//        return listOfKeys;
//    }
//    private ArrayList<T> storeOccurrencesKeys() {
//        tail = head;
//        while (tail != null) {
//            anotherListOfKeys.add(tail.value);
//            tail = tail.next;
//        }
//        return anotherListOfKeys;
//    }

    private class LinkedListBagUniqueIterator implements Iterator<T> {
        private int index = 0;
        private ArrayList<T> storeKeys() {
            ArrayList<T> innerListOfKeys= new ArrayList<T>();
            tail = head;
            while (tail != null) {
                innerListOfKeys.add(tail.value);
                tail = tail.next;
            }
            return innerListOfKeys;
        }
        ArrayList<T> listOfKeys = new ArrayList<T>(storeKeys());


        public boolean hasNext() {
            if (index < listOfKeys.size()) return true;
            return false;
        }
        public T next() { return listOfKeys.get(index++);}
    }
    public Iterator<T> iterator()
    {
        return new LinkedListBagUniqueIterator();
    }
    private class LinkedListBagIterator implements Iterator<T>
    {
        private int index = 0;
        private int count = 0;
        private ArrayList<T> storeKeys() {
            ArrayList<T> innerListOfKeys= new ArrayList<T>();
            tail = head;
            while (tail != null) {
                innerListOfKeys.add(tail.value);
                tail = tail.next;
            }
            return innerListOfKeys;
        }
        ArrayList<T> listOfKeys = new ArrayList<T>(storeKeys());

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

    public Iterator<T> allOccurrencesIterator()
    {
        return new LinkedListBagIterator();
    }


}
