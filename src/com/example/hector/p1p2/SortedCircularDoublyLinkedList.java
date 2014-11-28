package com.example.hector.p1p2;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedCircularDoublyLinkedList<E extends Comparable<E>> implements SortedList<E> {

	private class Node {
		private E value;
		private Node next;
		private Node prev;

		public E getValue() {
			return value;
		}
		public void setValue(E value) {
			this.value = value;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
		public Node getPrev() {
			return prev;
		}
		public void setPrev(Node prev) {
			this.prev = prev;
		}


	}

	private class ListIterator implements Iterator<E>{

		private Node nextNode;

		public ListIterator(int index){
			int iter = 0;
			for(nextNode = header.getNext();
					iter!=index;
					iter++,nextNode= nextNode.getNext() );

		}
		public ListIterator() {
			nextNode = header.getNext();
		}
		@Override
		public boolean hasNext() {
			return nextNode != null;
		}

		@Override
		public E next() {
			if (hasNext()){
				E result = this.nextNode.getValue();
				this.nextNode = this.nextNode.getNext();
				return result;
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

	private class ReverseListIterator implements ReverseIterator<E>{
		private Node prevNode;
		public ReverseListIterator(){
			Node temp = null;
			if (!isEmpty()){
				for (temp = header.getNext(); temp.getNext() != null; temp = temp.getNext());
				prevNode = temp;
			}
			else  {
				prevNode = header;
			}
		}
		public ReverseListIterator(int index){

			int iter = 0;
			for(prevNode = header.getNext();
					iter!=index;
					iter++,prevNode= prevNode.getNext());
		}
		@Override
		public boolean hasPrev() {
			return prevNode != header;
		}

		@Override
		public E prev() {
			if (hasPrev()){
				E result = prevNode.getValue();
				prevNode = prevNode.getPrev();
				return result;
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
	private Node header;
	private int currentSize;

	public SortedCircularDoublyLinkedList(){
		this.header = new Node();
		this.currentSize = 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new ListIterator();
	}

	@Override
	public Iterator<E> iterator(int index) {
		if(index<0||index>=currentSize){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		return new ListIterator(index);
	}

	public ReverseIterator<E> reverseIterator() {
		return new ReverseListIterator();
	}

	@Override
	public ReverseIterator<E> reverseIterator(int index) {
		if(index<0||index>=currentSize){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		return new ReverseListIterator(index);
	}
	@Override
	public void add(E obj) {

		if (obj == null){
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		Node newNode = new Node();
		newNode.setValue(obj); // new node to be added

		// add first element with reference to header and to ground
		// no elements in LL
		if ( this.isEmpty() ) {
			// add first element
			newNode.setPrev(this.header);
			newNode.setNext(this.header);
			this.header.setNext(newNode);
			this.header.setPrev(newNode);

			// if newNode is the greatest value
		} else if ( this.first().compareTo(newNode.getValue()) >= 0  ) {

			newNode.setNext(this.header.getNext());
			newNode.setPrev(this.header);
			this.header.getNext().setPrev(newNode);
			this.header.setNext(newNode);

			// check if new element is last element
		} else if (this.last().compareTo(newNode.getValue()) <= 0 ) { // add to end of SCDLL

			newNode.setPrev(this.header.getPrev());
			newNode.setNext(this.header);
			this.header.getPrev().setNext(newNode);
			this.header.setPrev(newNode);

			//insertion sort
		} else
		{
			Node currentNode = this.header.getNext(); // points to first element
			Node nextNode = this.header.getNext().getNext(); // point to next element
			boolean flag = false;
			while (nextNode != null) //
			{
				if (currentNode.getValue().compareTo(newNode.getValue()) <= 0 && nextNode.getValue().compareTo(newNode.getValue()) >= 0 )
				{
                    newNode.setNext(nextNode);
                    newNode.setPrev(currentNode);
                    currentNode.setNext(newNode);
                    nextNode.setPrev(newNode);
                    
					flag = true;
					break;
				}
				else
				{
					currentNode = nextNode; // loop to next two nodes
					nextNode = nextNode.getNext();
				}
	            if (!flag)
	            {
	                newNode.setNext(nextNode);
	                nextNode.setPrev(newNode);
	            }
			}
		}
		// increase total size
		this.currentSize++;
	}

	@Override
	public void add(int index, E obj) {
		if (obj == null){
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if ((index < 0) || (index > this.size())){
			throw new IndexOutOfBoundsException();
		}
		if (index == this.size()){
			this.add(obj);
		}
		else {
			Node temp = null; // marks position within the list
			int counter = 0; // counts position within the list
			for (temp = header.getNext(); counter < index; temp = temp.getNext(), counter++);
			Node newNode = new Node();
			newNode.setValue(obj);
			newNode.setPrev(temp.getPrev());
			temp.getPrev().setNext(newNode);
			newNode.setNext(temp);
			temp.setPrev(newNode);
			this.currentSize++;
		}
	}

	@Override
	public boolean remove(E obj) {
		if(this.currentSize==0){
			return false;
		}
		Node temp = this.header.getNext();
		for(;temp!=header;temp=temp.getNext()){
			if(temp.getValue().equals(obj)){
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				temp.setValue(null);
				temp.setNext(null);
				temp.setPrev(null);
				temp = null;
				this.currentSize--;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(int index) {
		if(index<0||index>=currentSize){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		Node nodeToRemove = this.header.getNext();
		for(int i =0; i!=index;i++, nodeToRemove=nodeToRemove.getNext());
		nodeToRemove.getPrev().setNext(nodeToRemove.getNext());
		nodeToRemove.getNext().setPrev(nodeToRemove.getPrev());
		nodeToRemove.setValue(null);
		nodeToRemove.setNext(null);
		nodeToRemove.setPrev(null);
		nodeToRemove = null;
		currentSize--;
		return true; 

	}

	public int removeAll(E obj) {
		int counter = 0;
		while(this.contains(obj)){
			this.remove(obj);
			counter++;
		}
		return counter;
	}

	@Override
	public E get(int index) {
		if(index<0||index>=currentSize){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		Node temp = this.header.getNext();
		for(int i=0;i!=index;i++,temp=temp.getNext());
		return temp.getValue();
	}

	@Override
	public E set(int index, E obj) {
		if (obj == null){
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		if ((index < 0) || (index >= this.size())){
			throw new IndexOutOfBoundsException();
		}
		else {
			int counter = 0;
			Node temp = null;
			for (temp = header.getNext(); counter < index; temp = temp.getNext(), counter++);
			E result = temp.getValue();
			temp.setValue(obj);
			return result;
		}
	}

	@Override
	public E first() {
		if (this.isEmpty()){
			return null;
		}
		else {
			return header.getNext().getValue();
		}
	}

	@Override
	public E last() {
		if (this.isEmpty()){
			return null;
		}
		else {
			Node temp = null;
			for (temp = this.header.getNext(); temp.getNext().getValue() != null; temp = temp.getNext());
			return temp.getValue();
		}


	}

	@Override
	public int firstIndex(E obj) {
		if (obj == null){
			throw new IllegalArgumentException("Parameter cannot be null.");
		}
		else {
			int counter = 0;
			Node temp = null;
			for (temp = header.getNext(); temp.getValue() != null; temp = temp.getNext(), counter++){
				if (temp.getValue().equals(obj)){
					return counter;
				}
			}
			return -1;
		}
	}

	@Override
	public int lastIndex(E obj) {
		if (obj == null){
			throw new IllegalArgumentException("Parameter cannot be null.");
		}
		else {
			int counter = 0, lastSeen = -1;
			Node temp = null;
			for (temp = header.getNext(); temp.getValue() != null; temp = temp.getNext(), counter++){
				if (temp.getValue().equals(obj)){
					lastSeen = counter;
				}
			}
			return lastSeen;
		}
	}

	@Override
	public int size() {
		return this.currentSize;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public boolean contains(E obj) {
		return this.firstIndex(obj) >= 0;
	}

	@Override
	public void clear() {
		while (!this.isEmpty()){
			this.remove(0);
		}
	}

}

