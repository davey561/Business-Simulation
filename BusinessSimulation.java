import java.util.Vector;

import java.util.Random;

import structure5.*;


/**

 * TODO: class description here.

 */

public abstract class BusinessSimulation {


	/* sequence of customers, prioritized by randomly-generated event time */

	protected PriorityQueue<Customer> eventQueue;


	/* series of service points where customers queue and are served */

	protected Vector<Queue<Customer>> servicePoints;


	/* current time step in the simulation */

	protected int time;


	/* seed for Random() so that simulation is repeatable */

	protected int seed;


	/* Used to bound the range of service times that Customers require */
	public static final int MIN_SERVICE_TIME = 5; //TODO: set appropraitely
	public static final int MAX_SERVICE_TIME = 20; //TODO: set appropriately

	/**

	 * Creates a BusinessSimulation.

	 * @post the step() function may be called.

	 *

	 * @param numCustomers number of random customers that appear in the simulation

	 * @param numSerivicePoints number of tellers in this simulation

	 * @param maxEventStart latest timeStep that a Customer may appear in the simulation

	 * @param seed used to seed a Random() so that simulation is repeatable.

	 */

	public BusinessSimulation(int numCustomers, int numServicePoints,
				  int maxEventStart, int seed) {
						this.servicePoints = new Vector<Queue<Customer>>();
						for(int i=0; i<numServicePoints; i++) {
							this.servicePoints.add(new QueueList());
						}
						this.eventQueue=generateCustomerSequence(numCustomers, maxEventStart, seed);
						this.time=0;
						this.seed=seed;
				}

	/**

	 * Generates a sequence of Customer objects, stored in a PriorityQueue.

	 * Customer priority is determined by arrival time

	 * @param numCustomers number of customers to simulate

	 * @param maxEventStart maximum timeStep that a customer arrives

	 *      in @eventQueue

	 * @param seed use Random(seed) to make customer sequence repeatable

	 * @pre

	 * @post

	 * @return A PriorityQueue that represents Customer arrivals,

	 *         ordered by Customer arrival time

	 */

	public static PriorityQueue<Customer> generateCustomerSequence(int numCustomers,

								       int maxEventStart, int seed) {

												 Random rnd = new Random(seed);

						PriorityQueue<Customer> line = new VectorHeap<Customer>();

						for(int i=0; i<numCustomers; i++) {

							line.add(new Customer(
								(int)(rnd.nextDouble() * maxEventStart)),
								(int)(rnd.nextDouble() * (MAX_SERVICE_TIME - MIN_SERVICE_TIME)) + MIN_SERVICE_TIME));
						}

						return line;

	}


	/**

	 * Advances 1 time step through the simulation.

	 *

	 * @post the simulation has advanced 1 time step

	 * @return true if the simulation is over, false otherwise

	 */

	abstract public boolean step();


	/**

	 * @return a string representation of the simulation

	 */

	public String toString() {

		// TODO: modify if needed.

		String str = "Time: " + time + "\n";
		str = str + "Event Queue: ";
		if (eventQueue != null) {
			str = str + eventQueue.toString();

		}

		str = str + "\n";


		if (servicePoints != null)  {

			for (Queue<Customer> sp : servicePoints) {

				str = str + "Service Point: " + sp.toString() + "\n";

			}

		}


		return str;

	}

}
