package com.example.hector.p1p2;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArrayList<E> implements SortedList<E>, Serializable {
    private int currentSize;

    private E elements[];

    public SortedArrayList() {
        this.currentSize = 0;
        this.elements = (E[]) new Object[5];
    }

    public boolean isMember(E e) {
        for (int i = 0; i < this.size(); i++) {
            if (this.elements[i].toString().equals(e.toString()))
                return true;
        }
        return false;
    }

    private void reAllocate() {
        E reAllocatedSpace[] = (E[]) new Object[2 * this.size()];
        for (int i = 0; i < this.currentSize; ++i)
            reAllocatedSpace[i] = this.elements[i];

        this.elements = reAllocatedSpace;

    }

    @Override
    public String toString(){
        String temp = "";
        for(int i=0; i < this.size(); i++){
            temp += this.elements[i].toString() + " ";
        }
        return temp;
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public boolean remove(E obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Argument object cannot be null.");
        }
        int target = -1;
        for (int i = 0; i < this.size(); ++i) {
            if (this.elements[i].equals(obj)) {
                target = i;
                break;
            }
        }
        if (target == -1) {
            return false;
        } else {
            for (int i = target; i < this.size() - 1; ++i) {
                this.elements[i] = this.elements[i + 1];
            }
            this.elements[this.currentSize--] = null;
            return true;
        }
    }

    @Override
    public boolean remove(int index) {
        if (index >= 0 && index < this.currentSize) {
            for (int i = index; i < this.currentSize - 1; ++i) {
                this.elements[i] = this.elements[i + 1];
            }
            this.elements[--this.currentSize] = null;
            return true;

        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public int removeAll(E obj) {
        int quantity = 0;
        while (this.remove(obj)) {
            quantity++;
        }
        return quantity;
    }

    @Override
    public E first() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.elements[0];
        }
    }

    @Override
    public E last() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.elements[this.size() - 1];
        }
    }

    @Override
    public E get(int target) {
        if (target >= 0 && target < this.size()) {
            return this.elements[target];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.size(); ++i) {
            this.elements[i] = null;
        }
        this.currentSize = 0;
    }

    @Override
    public boolean contains(E obj) {
        return this.firstIndex(obj) >= 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public int firstIndex(E obj) {
        for (int i = 0; i < this.size(); ++i) {
            if (this.elements[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndex(E obj) {
        for (int i = this.size() - 1; i >= 0; i--) {
            if (this.elements[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ReverseIterator<E> reverseIterator(int index) {
        return null;
    }

    @Override
    public void add(E obj) {
        if (obj == null)
            throw new IllegalArgumentException("Argument can't be null.");
        if (this.currentSize == this.elements.length)
            reAllocate();

        this.elements[this.currentSize++] = obj;

        ArrayList<E> temp = new ArrayList<E>();
        for (int n = 0; n < this.size(); n++)
            temp.add(this.elements[n]);

        // sort of objects.
        Collections.sort(temp, new Comparator<E>() { public int compare(final E a, final E d) {
            return (a.toString().compareTo(d.toString()));
        }
        });
        for (int s = 0; s < temp.size(); s++)
            this.elements[s] = temp.get(s);
    }


    @Override
    public void add(int index, E obj) {
        if (obj == null){
            throw new IllegalArgumentException("Argument object cannot be null.");
        }
        if (index == this.size()){
            this.add(obj);
        }
        else if (index >= 0 && index < this.currentSize){
            if (this.currentSize == this.elements.length){
                reAllocate();
            }
            // move everybody one spot to the back
            for (int i=this.currentSize; i > index; --i){
                this.elements[i] = this.elements[i-1];
            }
            // add element at position index
            this.elements[index] = obj;
            this.currentSize++;
        }
        else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public E set(int index, E obj) {
        if (obj == null){
            throw new IllegalArgumentException("Object cannot be null");
        }
        if (index>=0 && index < this.size()){
            E temp = this.elements[index];
            this.elements[index] = obj;
            return temp;
        }
        else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public ListIterator<E> iterator(int index) {
        return new ListIterator<E>(index);
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    private class ListIterator<E> implements Iterator<E>{

        private int currentPosition;

        public ListIterator(int index){
            this.currentPosition = index;
        }

        @Override
        public boolean hasNext() {
            return this.currentPosition < size();
        }

        @Override
        public E next() {
            if (this.hasNext()){
                return (E) elements[this.currentPosition++];
            }
            else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
