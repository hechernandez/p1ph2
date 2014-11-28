package com.example.hector.p1p2;

public interface ReverseIterator<E> {
	
	public boolean hasPrev();
	
	public E prev();
	
	public void remove();

}
