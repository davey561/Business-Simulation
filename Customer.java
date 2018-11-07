import structure5.*;

/**
 * TODO: class description here.
 */
public class Customer implements Comparable<Customer> {

	private int eventTime;
	private int serviceTime;

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
		this.eventTime=eventTime;
		this.serviceTime=serviceTime;
	 }

	/**
	 * Compares Customers by arrival time
	 * @param other Customer to compare against this.
	 */
	public int compareTo(Customer other) {
		return other.eventTime-this.eventTime;
	}

	public int getEventTime() {
		return eventTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public String toString() {
		return "";
	}
}
