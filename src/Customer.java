
public class Customer implements Comparable<Customer> {
	/*
	 * Keep track of how maany customers have been created
	 */
	private static int customerCount = 0;
	/*
	 * The number of the customer instance
	 */
	private int customerNumber;
	private int storeArrivalTime;
	private int shoppingLength;
	
	public Customer(int customerNumber, int simClock) {
		this.customerNumber = customerNumber;
		this.storeArrivalTime = simClock;
		this.shoppingLength = (int)(5 + Math.random() * (45 - 5 + 1));
		this.customerNumber++;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public int getStoreArrivalTime() {
		return storeArrivalTime;
	}

	public int getShoppingLength() {
		return shoppingLength;
	}
	
	public int getcheckoutArrivalTime() {
		return this.storeArrivalTime + this.shoppingLength;
	}
	
	public int getcheckoutLength() {
		return (int)Math.ceil(shoppingLength/5.0);
	}
	
	public String toString() {
		return "Customer Number: " + this.customerNumber + "\r\n" + " | Store Arrival Time: " + this.storeArrivalTime +
				"\r\n" + " | Shopping Length: " + this.shoppingLength + "\r\n" + " | Checkout Arrival Time: " + getcheckoutArrivalTime() + "\r\n" +
				" | Checkout Length: " + getcheckoutLength() + "\r\n" + " | Num of Customers: " + customerCount + "\r\n" + " --------" + "\r\n";
	}
	@Override
	public int compareTo(Customer customer) { 
		return getcheckoutArrivalTime() - customer.getcheckoutArrivalTime();
	}



}
