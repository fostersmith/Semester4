package linkedlist;

import java.io.Serializable;
import java.util.ListIterator;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

public class DLPositionalListCode implements ListIterator, Serializable {

	private Node head;
	private Node curPos;
	private Node lastItemPos;
	private int listSize;
	private String removeItem;
	
	public DLPositionalListCode() {
		head = new Node(null, null, null);
		head.next = head;
		head.previous = head;
		curPos = head;
		lastItemPos = null;
		listSize = 0;
	}
	
	public void add(Object obj) {
		Node newNode = new Node(obj, curPos.previous, curPos);
		curPos.previous.next = newNode;
		curPos.previous = newNode;
		listSize++;
		lastItemPos = null;
	}
	
	public void addAfter(Object obj) {
		Node newNode = new Node(obj, curPos.previous, curPos);
		curPos.previous.next = newNode;
		curPos.previous = newNode;
		listSize++;
		lastItemPos = null;
	}
	
	public void addAfter2(Object obj) {
		Node newNode = new Node(obj, curPos, curPos.next);
		curPos.next = newNode;
		newNode.next.previous = newNode;
		curPos = curPos.next;
		listSize++;
		lastItemPos = null;
	}
	
	public void addBefore(Object obj) {
		Node newNode = new Node(obj, curPos, curPos.next);
		curPos.next.previous = newNode;
		curPos.next = newNode;
		listSize++;
		lastItemPos = null;
	}

	public void addBefore2(Object obj) {
		Node newNode = new Node(obj, curPos.previous, curPos);
		curPos.previous = newNode;
		newNode.previous.next = newNode;
		curPos = curPos.previous;
		listSize++;
		lastItemPos = null;
	}
	
	public boolean hasNext() {
		if(curPos.next == head) return false;
		else return true;
	}
	
	public boolean hasPrevious() {
		if(curPos.previous == head) return false;
		else return true;
	}
	
	public Object next() {
		if(!hasNext()) 
			System.out.println("You are at the end of the list. The first item will be returned:");
		curPos = curPos.next;
		return curPos.previous.value;
	}
	
	public int nextIndex() {
		return currentIndex() + 1;
	}
	
	public Object previous() {
		if(!hasPrevious())
			System.out.println("You are at the beginning of the list. The last item will be returned:");
		curPos = curPos.previous;
		return curPos.value;
	}
	
	public int previousIndex() {
		return currentIndex() - 1;
	}
	
	public void remove() {
		lastItemPos = curPos;
		curPos = curPos.previous;
		curPos.next = lastItemPos.next;
		lastItemPos.next.previous = curPos;
		listSize--;
	}
	
	public void remove2() {
		removeItem = JOptionPane.showInputDialog("Input the object you would like to remove.");
		curPos = head.next;
		while(curPos != head && !curPos.value.toString().equals(removeItem)) {
			if(curPos.next == head) {
				System.out.println("The item couldn't be found");
				curPos = head;
			}
			else {
				next();
			}
		}
		if(curPos != head) {
			remove();
		}
	}
	
	public void set(Object obj) {
		if(curPos == null)
			throw new RuntimeException("There is no established item to set");
		curPos.value = obj;
	}
	
	public int currentIndex() {
		int counter = 0;
		Node tempCurPos = new Node();
		
		if(!hasNext())
			return -1;
		else {
			tempCurPos.next = curPos;
			curPos = head.next;
			
			while(curPos != tempCurPos.next) {
				next();
				counter++;
			}
			curPos = tempCurPos.next;
			return counter;
		}
	}
	
	public int size() {return listSize;}
	
	public String toString() {
		String str = "";
		for(Node node = head.next; node!= head; node = node.next) {
			str += node.value + "";
		}
		return str;
	}
	
	public String toBackwardString() {
		String str = "";
		for(Node node = head.previous; node != head; node = node.previous) {
			str += node.value + "";
		}
		return str;
	}
	
	//private inner class for Node
	private class Node implements Serializable
	{
	private Object value;
	private Node next;
	private Node previous;
	private Node()
	{
	value = null;
	previous = null;
	next = null;
	}
	private Node(Object value)
	{
	this.value = value;
	previous = null;
	next = null;
	}
	private Node(Object value, Node previous, Node next)
	{
	this.value = value;
	this.previous = previous;
	this.next = next;
	}}

	public static void main(String[] args) {
		DLPositionalListCode list = new DLPositionalListCode();
		Consumer<DLPositionalListCode> listDataPrint = new Consumer<DLPositionalListCode>() {
			@Override
			public void accept(DLPositionalListCode list) {
				System.out.println("Has previous: "+list.hasPrevious());
				System.out.println("Has next: "+list.hasNext());
				System.out.println("Size: "+list.size());				
			}
		};
		listDataPrint.accept(list);
		while(list.size() > 0)
			list.remove();
		for(char letter = 'a'; letter <= 'z'; ++letter) {
			list.add((Character)letter);
		}
		System.out.println(list);
		listDataPrint.accept(list);
		list.previous();
		System.out.println("Value of previous: "+list.curPos.value);
		list.set("$");
		for(int i = 0; i < 5; ++i)
			list.previous();
		System.out.println("Current value: "+list.curPos.value);
		System.out.println("Current index: "+list.currentIndex());
		System.out.println("Previous index: "+list.previousIndex());
		System.out.println("Next index: "+list.nextIndex());
		list.remove();
		System.out.println("List: "+list);
		System.out.println("End to start: "+list.toBackwardString());
		System.out.println("Size: "+list.size());
		System.out.println("Current value: "+list.curPos.value);
		list.remove2();
		System.out.println("List: "+list);
		System.out.println("End to start: "+list.toBackwardString());
		System.out.println("Size: "+list.size());
		System.out.println("Current value: "+list.curPos.value);
		while(list.hasPrevious())
			list.previous();
		while(list.hasNext()) {
			list.next();
			list.set("*");
		}
		System.out.println(list);
		for(String s : new String[] {"Happy", "Birthday", "to", "You"})
			list.addAfter(s);
		System.out.println(list);
		System.out.println(list.toBackwardString());
		
	}
}
