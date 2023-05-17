package stacks;

import linkedlist.LinkedList;

public class Stack {
	private LinkedList list;
	public Stack() {
		list = new LinkedList();
	}
	public boolean isEmpty() {
		return list.size() == 0;
	}
	public void push(Object item) {
		list.add(item);
	}
	public Object pop() {
		Object item = list.get(list.size());
		list.remove(list.size());
		return item;
	}
	public Object peek() {
		return list.get(list.size());
	}
	
}
