package com.example.hector.p1p2;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.example.hector.p1p2.ReverseIterator;
import com.example.hector.p1p2.SortedCircularDoublyLinkedList;

public class Test3 {
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
		this.list.remove("Al");
		this.list.add("Al");
		assertTrue(this.list.get(0).equals("Al"));
		this.list.remove("Al");
		assertFalse(this.list.get(0).equals("Al"));
		this.list.add("Zen");
		assertTrue(this.list.last().equals("Zen"));
	}

	@Test
	public void testSize() {
		assertTrue(this.list.size() == 7);
		this.list.add("Ned");
		this.list.add("Jil");
		this.list.remove("Xi");
		assertTrue(this.list.size() == 8);
		this.list.clear();
		assertTrue(this.list.size() == 0);
		this.list.add("Ron");
		assertTrue(this.list.size() == 1);
	}

	@Test
	public void testRemoveE() {
		this.list.remove("Ron");
		this.list.add("Ron");
		this.list.remove("Ron");
		assertTrue(!this.list.contains("Ron"));
		this.list.remove("Xi");
		assertTrue(!this.list.contains("Xi"));
		assertFalse(this.list.remove("Gen"));
		this.list.clear();
		assertFalse(this.list.remove("Gen"));
		this.list.add("Joe");
		assertTrue(this.list.remove("Joe"));
	}

	@Test
	public void testRemoveInt() {
		this.list.remove(2);
		assertTrue(!this.list.contains("Bob"));
		this.list.remove(0);
		this.list.add("Amy");
		this.list.add("Amy");
		this.list.add("Amy");
		this.list.remove(0);
		this.list.remove(0);
		this.list.remove(0);		
		assertTrue(!this.list.contains("Amy"));
		this.list.add("Amy");
		assertTrue(this.list.remove(1));

	}

	@Test
	public void testRemoveAll() {
		assertTrue(this.list.size() !=0);
		this.list.clear();
		assertTrue(this.list.size() == 0);
		this.list.add("Joe");
		this.list.add("Ned");
		this.list.add("Ned");
		this.list.add("Ned");
		this.list.add("Ned");
		assertTrue(this.list.removeAll("Ned") == 4);
		assertTrue(this.list.size() == 1);
	}

	@Test
	public void testFirst() {
		assertTrue(this.list.first().equals("Amy"));
		this.list.add("Al");
		assertTrue(this.list.first().equals("Al"));
		this.list.add("Abu");
		this.list.add("Abi");
		assertTrue(this.list.first().equals("Abi"));
	}

	@Test
	public void testLast() {
		assertTrue(this.list.last().equals("Xi"));
		this.list.add("Zoe");
		assertTrue(this.list.last().equals("Zoe"));
		this.list.remove("Zoe");
		this.list.add("Zen");
		assertTrue(this.list.last().equals("Zen"));
	
	}

	@Test
	public void testGet() {
		assertTrue(this.list.get(1).equals("Apu"));
		assertTrue(this.list.get(0).equals("Amy"));
		this.list.clear();
		this.list.add("Jim");
		this.list.add("Jom");
		assertTrue(this.list.get(1).equals("Jom"));
		assertTrue(this.list.get(0).equals("Jim"));

		
	}

	@Test
	public void testClear() {
		this.list.clear();
		assertTrue(this.list.size() ==0);
		this.list.add("Ned");
		this.list.add("Kim");
		this.list.clear();
		assertTrue(this.list.size() ==0);

	}

	@Test
	public void testContains() {
		assertTrue(this.list.contains("Xi"));
		assertFalse(this.list.contains("Yu"));
		assertTrue(this.list.remove("Xi"));
		assertFalse(this.list.contains("Xi"));
		assertFalse(this.list.remove("Xi"));

	}

	@Test
	public void testIsEmpty() {
		assertFalse(this.list.isEmpty());
		this.list.clear();
		assertTrue(this.list.isEmpty());
	}

	@Test
	public void testIteratorInt() {
		Iterator<String> iter = this.list.iterator(0);
		assertTrue(iter.hasNext());
		assertTrue(iter.next().equals("Amy"));

		iter = this.list.iterator(2);
		assertTrue(iter.hasNext());
		assertTrue(iter.next().equals("Bob"));
		
		this.list.add("Abu");
		iter = this.list.iterator(0);
		assertTrue(iter.hasNext());
		assertTrue(iter.next().equals("Abu"));
		assertTrue(iter.hasNext());
		assertTrue(iter.next().equals("Amy"));

	}

	@Test
	public void testFirstIndex() {
		this.list.add("Bob");
		assertTrue(this.list.firstIndex("Bob") == 2);
		assertTrue(this.list.firstIndex("Jil") < 0);
		this.list.add("Bil");
		assertTrue(this.list.firstIndex("Bob") == 3);

	}

	@Test
	public void testLastIndex() {
		this.list.add("Bob");
		this.list.add("Bob");
		this.list.add("Bob");
		assertTrue(this.list.lastIndex("Bob") == 5);
		assertTrue(this.list.lastIndex("Jil") < 0);
	}


}
