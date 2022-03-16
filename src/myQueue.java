import java.util.ArrayList;
/**
 * 
 * @author dalto
 *
 * @param <E> - the type of the object the queue holds
 */
public class myQueue<E>  {
	/*
	 * The actual storage
	 */
	private ArrayList<E> list = new ArrayList<E>();
	
	/** add an object<E> to the list, which this will be customers that are passed through */
	public void enqueue(E c) {
		list.add(c);
	}
	
	/** check to see if the list is empty, if it is return a null value
	 * otherwise remove the first value and return it
	 * @return - the first object in the queue
	 */
	public E dequeue() {
		if(list.isEmpty()) {
			return null;
		} else {
			return list.remove(0);
		}
	}
	
	/**Will return how many objects are in the queue */
	public int getSize() {
		return list.size();
	}
	
	/**
	 * @return true if the arrayList is empty and return false otherwise
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
}
