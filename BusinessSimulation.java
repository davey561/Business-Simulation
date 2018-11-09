import java.util.Vector;

import java.util.Random;

import structure5.*;


/**
 * BusinessSimulation provides the overarching methods for simulating the lines of a store as customers arrive and leave and tellers attend to customers and wait for new ones.
 * The two class's two children are simulate two different store-policies of how lines should form: MultiQueue and OneQueue
 * @author DaveyMorse
 * @author Stephen Willis
 */

public abstract class BusinessSimulation {
	//Declarations
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
	 * @param numCustomers number of randomly generated customers that appear in the simulation
	 * @param numSerivicePoints number of tellers in this simulation
	 * @param maxEventStart latest timeStep that a Customer may appear in the simulation
	 * @param seed used to seed a Random() so that simulation is repeatable.
	 */
	public BusinessSimulation(int numCustomers, int numServicePoints, int maxEventStart, int seed) {
						this.servicePoints = new Vector<Queue<Customer>>(); //a vector of service points, the line of each of which is represented by a queue of Customer objects
						//for each service point (ie for each line)
						for(int i = 0; i<numServicePoints; i++) {
							this.servicePoints.add(new QueueList<Customer>()); //initialize the given line as a Queue of customers
						}
						this.eventQueue = generateCustomerSequence(numCustomers, maxEventStart, seed); //generate the queue of events. an event is a customer entering the store at a specific time
						this.time = 0; //initialize time as 0
						this.seed = seed;
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

	public static PriorityQueue<Customer> generateCustomerSequence(int numCustomers, int maxEventStart, int seed) {
		Random rnd = new Random(seed); //random generator
		PriorityQueue<Customer> line = new VectorHeap<Customer>(); //the preloaded 'line of customers' to enter the stored
			//using a type of PriorityQueue (here, VectorHeap) because it can remove values in increasing order, which is exactly what we need for customers arriving at a store
		for(int i=0; i<numCustomers; i++) {
			line.add(new Customer(
				(int)(rnd.nextDouble() * maxEventStart),
				(int)(rnd.nextDouble() * (MAX_SERVICE_TIME - MIN_SERVICE_TIME)) + MIN_SERVICE_TIME
			));
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
	public void print(){
		System.out.println(this.toString());
	}

}
