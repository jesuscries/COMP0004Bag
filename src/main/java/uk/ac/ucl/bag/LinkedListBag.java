package uk.ac.ucl.bag;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

public class LinkedListBag<T extends Comparable> extends AbstractBag<T> {
    private int maxSize;
    private LinkedList<Element<T>> contents;

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
    public LinkedListBag() throws BagException { this(MAX_SIZE); }
    public LinkedListBag(int maxSize) throws BagException {

    }
}
