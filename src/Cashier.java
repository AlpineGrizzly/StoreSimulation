
public class Cashier extends myQueue<Customer>{
	private int finishTime;
	
	/** Initialize the finishtime for each of the cashier objects to 0*/
	Cashier() {
		this.finishTime = 0;
	}
	
	/**Return the next time the cashier can take another customer */
	public int getfinishTime() {
		return this.finishTime;
	}
	
	/**Will put a customer in the cashier's queue and return true */
	public boolean addCustomer(Customer c) {
		if(c == null) {
			return false;
		} else {
		super.enqueue(c);
		return true;
		}
	}
	
	/** Will take the next customer and set its finish time to the customer's checkout length */
	public Customer serveNextcustomer(int simClock) {
		/**If the queue is empty, then there are no customers to serve.
		 * Return a null value
		 */
		if(super.isEmpty() == true) {
		return null;
		/** Otherwise, make a customer object and sets it equal to the customer that will be dequeued
		 * take that customers finish time and add it to the simclock to tell the cashier when it can take a customer again
		 */
		} else {
			Customer removed = super.dequeue();
			finishTime = simClock + removed.getcheckoutLength();
			return removed;
		}

	}
}
		

