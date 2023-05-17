package linkedlist;

public class LinkedList {
	private Node head;
	private int listCount;
	
	public LinkedList() {
		head = new Node(null);
		listCount = 0;
	}
	
	public void add(Object data) {
		Node temp = new Node(data);
		Node current = head;
		while(current.getNext() != null) {
			current = current.getNext();
		}
		current.setNext(temp);
		listCount++;
	}
	public void add(Object data, int index) {
		Node temp = new Node(data);
		Node current = head;
		for(int i = 1; i < index && current.getNext() != null; ++i) {
			current = current.getNext();
		}
		temp.setNext(current.getNext());
		current.setNext(temp);
		listCount++;
	}
	public Object get(int index) {
		if(index <= 0) {
			return null;
		}
		
		Node current = head.getNext();
		for(int i = 1; i < index; i++) {
			if(current.getNext() == null)
				return null;
			current = current.getNext();
		}
		return current.getData();	
	}
	
}
