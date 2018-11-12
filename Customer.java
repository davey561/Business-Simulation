import structure5.*;

/**
 * The customer class contains characteristics of a customer walking into a store, in a simulation of that store. The simulation is meant to demonstrate how long customers would be waiting with different times required to service times and a certain number of tellers and lines in the store
 *@author Davey Morse and Stephen Willis
 */
public class Customer implements Comparable<Customer> {
	//Instance variables of two types:
	//1) invariant of characteristics of the particular simulation
	private int eventTime; //the time the customer arrives at the store
	private int serviceTime; //the units of time necessary to properly serve the customer

	//2) dependent on conditions within the simulation
	private int serviceBegins; //the time at which their service begins after waiting in line in the simulation
	private int waitTime; //the units of time for which they waited in the simulation

	/**
	 * Creates a Customer that arrives at time step @eventTime and
	 * requires @serviceTime time steps to be satisfied after
	 * arriving at a service point.
	 * @param eventTime time step that this Customer appears in the
	 *        simulation
	 * @param serviceTime number of time steps required to service this
	 *        Customer.
	 */
	public Customer(int eventTime, int serviceTime) {
		this.eventTime = eventTime;
		this.serviceTime = serviceTime;
		this.serviceBegins = -1;
	 }

	/**
	 * Compares Customers by arrival time
	 * @param other Customer to compare against this.
	 * @return a postive integer if this customer enters store after other customer, negative if this customer enters store before customer
	 */
	public int compareTo(Customer other) {
		return this.eventTime-other.eventTime; //this order is important--lower value--> higher priority (higher priority is closer to front of line)
	}

	/**
		* Gets the time at which the customer arrives
		* pre: eventTime should not be null (i.e. customer should be initialized, which it will be)
		* post: fetches and returns event time
		*/
	public int getEventTime() {
		return eventTime;
	}

	/**
		* Gets the number of units of time necessary for the customer to be fully served
		* pre: eventTime should not be null (i.e. customer should be initialized, which it will be)
		* post: fetches and returns serviceTime
		*/
	public int getServiceTime() {
		return serviceTime;
	}

	/**
		* Gets the time at which the customer starts being served in a given simulation
		* pre: for the method's intended purposes, should only really be called in the unique() method of each particular type of businessSim (either OneQueue or MultiQueue)
		* post: fetches and returns serviceTime
		*/
	public int getServiceBegins() {
		return serviceBegins;
	}
	/**
		* Sets the time at which service begins for this particular customer in a given simulation
		* pre: i should be larger than the customer's event time, as their service can't begin before they enter the store
		* post: sets serviceBegins equal to the given int i
		* @param i the given integer
		*/
	public void setServiceBegins(int i) {
	  serviceBegins = i;
	}

	/**
		* Gets the number of units of time for which the customer had to wait in a given business simulation
		* pre: should only be called after Wait Time has been calculated and set, i.e. after the customer has started being served
		* post: fetches and returns the amount of time for which the customer had to wait in line
		*/
	public int getWaitTime() {
		return waitTime;
	}
	/**
		* Sets the number of units of time for which the customer had to wait in a given business simulation--NOW OBSOLETE GIVEN THE BETTER VERSION BELOW
		* pre: i should be a positive value, wait time is generally positive
		* post: sets the wait time to the given int i
		* @param i the inputted wait time
		*/
	public void setWaitTime(int i) {
	  waitTime = i;
	}
	/**
		* Sets the number of units of time for which the customer had to wait in a given business simulation as the amount of time elapsed between when they arrived at the store and when they started being served
		* pre: serviceBegins needs to have been set already. so this function can only be called at or after the the point when the customer's service has begun (when they're leaving the line to be served)
		* post: sets the wait time to the given int i
		* @param i the inputted wait time
		*/
	public void setWaitTime() {
	  waitTime = getServiceBegins()-getEventTime();
	}
	/**
		* Gives string representation of the customer object
		*
		*/
	public String toString() {
		String s = "";
		if(serviceBegins!=-1) s+= ", service start time: " + getServiceBegins();
		if(waitTime!=-1) s+= ", amount of time waited: " + getWaitTime();
		return ("<Customer| event time: " + getEventTime() + ", service time: " + getServiceTime() + s + ">\n");
	}
	/**
		*Main method meant to test/debug this class
		*/
	public static void main(String[] args) {
		Customer c = new Customer(10, 5);
		c.setServiceBegins(13);
		c.setWaitTime(); //Should set to 3
		System.out.println(c.toString());
	}
}
