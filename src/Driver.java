import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Driver {

	public static void main(String[] args) throws IOException {
		/**Scanner that will be used to get input from the user on the parameters of the simulation */
		Scanner in = new Scanner(System.in);
		/** @customersCreated will keep track of how many customers have entered the store */
		int customersCreated = 0;
		/** @simClock will keep track of the time in the simulation */
		int simClock = 1;
		/** @howLong will ask for an input of how long the simulation shoud run for */
		int howLong = getTime();
		/** @numCustomers will ask the user for about how many customers will enter each hour */
		int numCustomers = getCustomers();
		/** @numLanes will ask for how many cashiers will be serving customers */
		int numLanes = getLanes();
		/** @served will keep a tally of how many customers have been served */
		int served = 0;
		/** @totalwaitTime will keep track of the total amount of minutes customers have been waiting */
		int totalwaitTime = 0;
		/**@customersInline will keep track of how many customers are in line at the end of the simulation */
		int customersInline = 0;
		
		/**The cashier array will be number of lanes big and will iterate through and create a cashier object for each space */
		Cashier[] cashArray = new Cashier[numLanes];
		/**customerList will create a new list that will hold all the customers that will enter through the store */
		MySortedList<Customer> customerList = new MySortedList<Customer>();
		/** putting a cashier in each spot of the cashier array*/
		for(int i = 0; i<numLanes; i++) {
			cashArray[i] = new Cashier();
		}
		/** Here are the two files that will be used during the simulation to keep track of the summary and details */
		File simSummary = new File("simSummary.txt");
		File simDetails = new File("simDetails.txt");
		PrintWriter pw = new PrintWriter(simDetails);
		
		/** Header for the simDetails */
		pw.printf("Details all customers served during the simulation:"); 
		pw.println(); 
		pw.println();
		pw.close();
		in.close();
		
		/**Simulation will take place as long as the time that the user has inputed is not equal to the iterator
		 * that will be keeping track of what time it is in the simulation
		 */
		while(howLong != simClock) {
			/** First thing to do when the simulation starts is to refer to the customerArrived method that will determine whether or not
			 * a customer has entered the store based on a probability algorithym
			 */
			if(customerArrived(numCustomers) == true) {
				/**If a customer is created, then increment the number of customersCreated and add it to 
				 * the sorted list of customers
				 * The parameters for the new customer will give it a number and also assign the time that they
				 * entered the store
				 */
				customersCreated++;
				customerList.add(new Customer(customersCreated, simClock));
			}
			/** int min will find the shortest line amongst the cashiers and return the lane number */
			int min = getShortest(cashArray, simClock);
			/**Check to see if there is a customer in the list */
			if(customerList.getFirst() != null ) {
				/** Now check to see whether that customer is ready to be checked out yet, according to their checkArrivalTime,
				 * which will return the simClock time that they should be put into a lane
				 */
				while(customerList.getFirst().getcheckoutArrivalTime() == simClock) {		
					/**If that person is ready to be checked out, add the customer to the smallest line in the cashier array at min, 
					 * and remove it from the sorted list
					 */
				cashArray[min].addCustomer(customerList.removeFirst());
				}
			}
			/**Next iterate through the lanes */
				for(int i = 0; i < numLanes; i++) {
					/**Check to see whether or not a cashier can serve their next customer*/
					if(cashArray[i].getfinishTime() <= simClock) {
						/** If they are able to, serve the next customer which will return null if there is no customer to be served */
						Customer beingServed = cashArray[i].serveNextcustomer(simClock);
						/** If they customer being served is not a null value, then they must be checked out */
						if(beingServed != null) {
							/** Increment the number of customers being served and also get their wait time to be used for the final
							 * data collection
							 */
							served++;
							totalwaitTime += beingServed.getcheckoutLength();
							/** Now we write the details of the customer that was checked out */
							try { 
							FileWriter fw = new FileWriter(simDetails, true);
							pw = new PrintWriter(fw);
							pw.println("Simclock: " + simClock);
							pw.println("Customer " +  beingServed.getCustomerNumber() + " is being served on lane " + (i + 1));
							pw.println("Customer shopped for " + beingServed.getShoppingLength() + " minutes before entering the checkout lane at simClock time " + (simClock - beingServed.getcheckoutLength()));
							pw.println("Customer " + beingServed.getCustomerNumber() + " arrived at the checkout line at simClock time " + (simClock - beingServed.getcheckoutLength()));
							pw.println("Customer " + beingServed.getCustomerNumber() + " waited in line for " + (double)beingServed.getcheckoutLength() + " minutes.");
							pw.println("-----------------------------------------------------------------------------");
							pw.close();
							} catch (Exception e) {
								simSummary = new File("simDetails.txt");
							}
						}
					}
				}
				/** This minute is over, now the loop starts over */
				simClock++;


			}
		/**Get the customers that were still in line when the simulation ended and add it to the total */
		for(int i = 0; i < numLanes; i++) {
			customersInline += cashArray[i].getSize();
		}

		/**Final summary information will be printed to the simSummary text file here which is located at
		 * simSummary.txt
		 * The details for the simulation above will also be located in the 
		 * simDetails.txt
		 */
		try {
			pw = new PrintWriter(simSummary);
			/**Header*/
			pw.println("Simulation used the following variables: ");
			/**Outputs how many lanes were in the simulation */
			pw.println("           Number of checkout lanes: " + numLanes);
			/**Outputs how long the simulation was */
			pw.println("           Length of the simulation, in minutes: " + howLong);
			/** Outputs how many customers are expected to come in each hour */
			pw.println("           Number of customers expected each hour: " + numCustomers);
			/**Outputs how many customers actually entered the store during the simulation */
			pw.println("Number of customers entering store during simulation: " + customersCreated);
			/**Outputs how many customers were still shopping when the simulation ended */
			pw.println("Number of customers still shopping: " + (customersCreated - served - customersInline));
			/**Outputs how many customers were still in line when the simulation ended */
			pw.println("Number of customers still in line: " + customersInline);
			/**Outputs how many customers were served during the simulation */
			pw.println("Number of customers served: " + served);
			/**Outputs the average wait time in line amongst all the customers */
			pw.printf("Average customer wait time: %.2f", (double)totalwaitTime/(double)served);
			/**Close the printWriter*/
			pw.close();
			
		} catch(Exception e) {
			/**If the file doesn't exist, create a new one */
			simSummary = new File("simSummary");
		}
		
		/**Tells the user where they can find the files and also lets them know that the simulation has been completed */
		System.out.println("Simulation has completed, see files: ");
		System.out.println("simSummary.txt, for a summary of the simulation");
		System.out.println("simDetails.txt, for the details of the simulation");

		}
		
	/***
	 * This method will return the index of the lane that has the shortest finishTime and will be used to determine where
	 * to place customers
	 * @param cashArray - this will be used to iterate through and check the finish times of each cashier in its array
	 * @param simClock - this will be used to determine whether or not the cashier is ready to take another customer
	 * @return - the index of the shortest lane
	 */
	public static int getShortest(Cashier[] cashArray, int simClock) {
		int min = 0;
			for(int i = 0; i < cashArray.length; i++) {
				if(cashArray[i].getfinishTime() <= simClock && cashArray[i].getSize() < cashArray[min].getSize()) {
					min = i;
				}
				
			}
			return min;
		}

	/***
	 * This method will get an input from the user to see how many checkout lanes they want the simulation
	 * to have. Should return a value greater than or equal to 1
	 * @return - number of checkout lanes
	 */
	public static int getLanes() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter amount of open Checkout Lanes (>= 1): ");

		int numLanes = 0;
		do {
			try {
				numLanes = in.nextInt();
				if(numLanes >= 1) {
				break;
				} else {
				System.out.print("Please enter a valid choice: ");
				}
		} catch(Exception e) {
			
			System.out.print("Please enter a valid choice: ");
		}
			in.nextLine();
		} while(true);
		return numLanes;
	}
	
	/***
	 * This method will take an input from the user to see how long that simulation should lasts.
	 * Should be a time greater than or equal to 10
	 * @return
	 */
	public static int getTime() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the length in minutes of the simulation (>= 10): ");
		
		int length = 0;
		do {
			try {
				length = in.nextInt();
				if(length >= 10) {
				break;
				} else {
				System.out.print("Please enter a valid choice: ");
				}
		} catch(Exception e) {
			
			System.out.print("Please enter a valid choice: ");
		}
			in.nextLine();
		} while(true);
		return length;
	}
	
	/***
	 * This method will return how many customers roughly will come in to the store on the hour
	 * it will only take a value that is between 1 and 60
	 * @return - about the number of customers that will enter the store
	 */
	public static int getCustomers() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the amount of Customers to be entering the store each hour (1-60):  ");
		
		int customers = 0;
		do {
			try {
				customers = in.nextInt();
				if(customers >= 1 && customers <= 60) {
				break;
				} else {
				System.out.print("Please enter a valid choice: ");
				}
		} catch(Exception e) {
			
			System.out.print("Please enter a valid choice: ");
		}
			in.nextLine();
		} while(true);
		return customers;
	}
	
	/***
	 * This method will be a probablity function that will use the rough number of customers that are to enter the store
	 * on the hour, and use that to determine whether or not a customer will come in
	 * @param numCustomers - number of customers that may enter the store. Used as numerator
	 * @return - whether or not a customer has entered the store
	 */
	public static boolean customerArrived(int numCustomers) {
		double prob = (numCustomers/(60.0))*100;
		int random = (int)Math.floor(Math.random()*100);
		if(prob >= random) {
			/*A customer has  entered */
			return true;
		} else {
			/*A customer has not entered */
			return false;
		}
	}

}
