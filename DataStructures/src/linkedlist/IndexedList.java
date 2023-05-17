package linkedlist;

public interface IndexedList {

	public void add(int index, Object obj);
	
	public Object get(int index);
	public boolean isEmpty();
	public boolean isFull();
	public Object remove(int index);
	public Object set(int index, Object ogj);
	public int size();
	public String toString();
	
}
