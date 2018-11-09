import structure5.*;

/**
 * TODO: class description here.
 */
public class Customer implements Comparable<Customer> {

	// TODO: Implement this class, adding member variables,
	// methods, and pre/post conditions as needed.
	// When you are done, please remove all completed TODO comments.

	/**
	 * Creates a Customer that arrives at time step @eventTime and
	 * requires @serviceTime time steps to be satisfied after
	 * arriving at a service point.
	 * @param eventTime time step that this Customer appears in the
	 *        simulation
	 * @param serviceTime number of time steps required to service this
	 *        Customer.
	 */
	public Customer(int eventTime, int serviceTime) { }
	/**
	 * Compares Customers by arrival time
	 * @param other Customer to compare against this.
	 */
	public int compareTo(Customer other) {
		return 0;
	}
	public String toString() {
		return "";
	}
}
