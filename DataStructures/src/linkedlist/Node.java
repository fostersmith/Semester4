package linkedlist;

public class Node {
	Node next;
	Object data;
	public Node(Object _data) {
		next = null;
		data = _data;
	}
	
	public Node(Object _data, Node _next) {
		next = _next;
		data = _data;
		
	}
	public Object getData() {
		return data;
	}
	public void setData(Object _data) {
		data = _data;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node _next) {
		next = _next;
	}
	
}
