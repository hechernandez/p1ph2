package com.example.hector.p1p2;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.example.hector.p1p2.ReverseIterator;
import com.example.hector.p1p2.SortedCircularDoublyLinkedList;

public class Test1 {
	private SortedCircularDoublyLinkedList<String> list;

	@Before
	public void setUp() throws Exception {
		this.list = new SortedCircularDoublyLinkedList<String>();
		this.list.add("Ron");
		this.list.add("Amy");
		this.list.add("Apu");
		this.list.add("Ned");
		this.list.add("Xi");
		this.list.add("Bob");
		this.list.add("Cal");
		
	}

	@Test
	public void testAdd() {
		this.list.add("Al");
		assertTrue(this.list.get(0).equals("Al"));
		this.list.add("Zen");
		assertTrue(this.list.last().equals("Zen"));
	}

	@Test
	public void testSize() {
		assertTrue(this.list.size() == 7);
		this.list.add("Ned");
		assertTrue(this.list.size() == 8);
	}

	@Test
	public void testRemoveE() {
		this.list.remove("Ron");
		assertTrue(!this.list.contains("Ron"));
		this.list.remove("Xi");
		assertTrue(!this.list.contains("Xi"));
	}

	@Test
	public void testRemoveInt() {	
	    this.list.remove(2);
		assertTrue(!this.list.contains("Bob"));
		this.list.remove(0);
		assertTrue(!this.list.contains("Amy"));
	}

	@Test
	public void testRemoveAll() {
		assertTrue(this.list.size() == 7);
		this.list.add("Xi");
		this.list.removeAll("Xi");
		assertTrue(this.list.size() == 6);	
	}

	@Test
	public void testFirst() {
		this.list.add("Al");
		assertTrue(this.list.first().equals("Al"));
		this.list.add("Abi");
		assertTrue(this.list.first().equals("Abi"));
		this.list.add("Aba");
		assertTrue(this.list.first().equals("Aba"));

	}

	@Test
	public void testLast() {
		assertTrue(this.list.last().equals("Xi"));
		this.list.add("Zoe");
		assertTrue(this.list.last().equals("Zoe"));
		this.list.add("Zulu");
		assertTrue(this.list.last().equals("Zulu"));		
	}

	@Test
	public void testGet() {
		
		assertTrue(this.list.get(1).equals("Apu"));
		assertTrue(this.list.get(0).equals("Amy"));
		assertTrue(this.list.get(2).equals("Bob"));
		this.list.remove(0);
		this.list.remove(0);
		assertTrue(this.list.get(0).equals("Bob"));
	

	}

	@Test
	public void testClear() {
		this.list.clear();
		assertTrue(this.list.size() ==0);
		this.list.add("Joe");
		this.list.clear();
		assertTrue(this.list.size() ==0);	
	}

	@Test
	public void testContains() {
		assertTrue(this.list.contains("Xi"));
		assertFalse(this.list.contains("Yu"));
		this.list.remove("Xi");
		assertFalse(this.list.contains("Xi"));

	}

	@Test
	public void testIsEmpty() {
		assertFalse(this.list.isEmpty());
		this.list.clear();
		assertTrue(this.list.isEmpty());
		this.list.add("Jil");
		assertTrue(!this.list.isEmpty());
		this.list.clear();
		assertTrue(this.list.isEmpty());

	}

	@Test
	public void testIteratorInt() {
		Iterator<String> iter = this.list.iterator(0);
		assertTrue(iter.hasNext());
		assertTrue(iter.next().equals("Amy"));

	}

	@Test
	public void testFirstIndex() {
		this.list.add("Bob");
		assertTrue(this.list.firstIndex("Bob") == 2);
		assertTrue(this.list.firstIndex("Jil") < 0);
	}

	@Test
	public void testLastIndex() {
		this.list.add("Bob");
		assertTrue(this.list.lastIndex("Bob") == 3);
		assertTrue(this.list.lastIndex("Jil") < 0);
	}

	
	
	

}
