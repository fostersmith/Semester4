package linkedlist;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class SLIndexedListCode implements IndexedList, Serializable {

	private Node head;
	private int listSize;
	private Node nodeAtIndex;
	private Node nodeBefore;
	private Node pointer;
	
	public SLIndexedListCode() {
		head = new Node("!", null);
		head = new Node("ball", head);
		head = new Node("the", head);
		head = new Node("Throw", head);
		listSize = 4;
	}
	
	public void add(int index, Object obj) {
		if(index < 0 || index >= listSize) {
			throw new RuntimeException("Index= "+index + " is out of the list bounds");
		}
		if(index == 0) {
			head = new Node(obj, head);
		}
		else {
			locateNode(index);
			nodeBefore.next = new Node(obj, nodeAtIndex);
		}
		listSize++;
	}
	
	public boolean isEmpty() {
		return listSize == 0;
	}
	
	public boolean isFull() {
		return false;
	}

	public Object get(int index) {
		if(index < 0 || index >= listSize) {
			throw new RuntimeException("Index = "+index + " is out of the list bounds");
		}
		locateNode(index);
		return nodeAtIndex.value;
	}
	
	public Object remove(int index) {
		if(index < 0 || index >= listSize) {
			throw new RuntimeException("Index = "+index+" is out of the list bounds");
		}
		
		Object removedObj = null;
		
		if(index == 0) {
			removedObj = head.value;
			head = head.next;
		}
		else {
			locateNode(index);
			nodeBefore.next = nodeAtIndex.next;
		}
		listSize--;
		return removedObj;
	}
	
	public Object set(int index, Object obj) {
		Object replacedObj = null;
		try {
			if(index == 0) {
				replacedObj = head.value;
				head.value = obj;
			}
			else {
				locateNode(index);
				replacedObj = nodeAtIndex.value;
				nodeAtIndex.value = obj;
			}
		} catch(RuntimeException e) {
			JOptionPane.showMessageDialog(null, "Index = "+index+" is out of the list bounds", "Out of Bounds Error",JOptionPane.ERROR_MESSAGE);
		}
		return replacedObj;
	}
	
	public int size() {return listSize;}
	public String toString() {
		String message = "";
		for(Node pointer = head; pointer != null; pointer = pointer.next) {
			message += pointer.value + "";
		}
		return message;
	}
	
	private void locateNode(int index) {
		nodeBefore = null;
		nodeAtIndex = head;
		for(int i = 1; i < listSize && i <= index; i++) {
			nodeBefore = nodeAtIndex;
			nodeAtIndex = nodeAtIndex.next;
		}
		if(index == listSize) {
			nodeBefore = nodeAtIndex;
			nodeAtIndex = null;
		}
	}
	
	private class Node{
		private Object value;
		private Node next;
		
		private Node() {
			this(null, null);
		}
		
		private Node(Object value, Node next) {
			this.value = value;
			this.next = next;
		}
	}
	
	public static void main(String[] args) {
		SLIndexedListCode list = new SLIndexedListCode();
		list.locateNode(0);
		
		for(Node pointer = list.head; pointer != null; pointer = pointer.next){
			System.out.println(pointer.value);
		}
		
		list.set(2, "blue");
		System.out.println(list);
		list.remove(3);
		System.out.println("List empty: "+list.isEmpty());
		list.set(4, "football");
		System.out.println("List is of size "+list.size());
		
	}
}
