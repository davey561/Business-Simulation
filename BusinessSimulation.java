import java.util.Vector;

import java.util.Random;

import structure5.*;


/**
 * BusinessSimulation provides the overarching methods for simulating the lines of a store as customers arrive and leave and tellers attend to customers and wait for new ones.
 * The two class's two children are simulate two different store-policies of how lines should form: MultiQueue and OneQueue
 * @author Davey Morse
 * @author Stephen Willis
 */

public abstract class BusinessSimulation {
	//Declarations
	/* sequence of customers, prioritized by randomly-generated event time */
	protected PriorityQueue<Customer> eventQueue;
	protected static Vector<Customer> customerList; //copy of event queue that doesn't get destroyed during simulation, so that can do calculations over entire set of customers after simulation is over (most importantly, to average all their wait times)

	/* series of service points where customers queue and are served */
	protected Vector<Queue<Customer>> servicePoints;
	int numServicePoints; //store this, because this is the number of tellers, important information for OneQueue
	int numCustomers;
	/* current time step in the simulation */
	protected int time;
	/* seed for Random() so that simulation is repeatable */
	protected int seed;
	/* Used to bound the range of service times that Customers require */
	public static final int MIN_SERVICE_TIME = 5; //TODO: set appropraitely
	public static final int MAX_SERVICE_TIME = 20; //TODO: set appropriately

	//keep track of when each line's first customer started being served.
  protected int [] start_times; //Each index corresponds to a service point; each int is the time at which the customer currently being served in that line started being served
  protected int wait_time; //the sum of all wait times of the customers, to be divided by the total number of customers before returned

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
						this.numServicePoints = numServicePoints;
						this.numCustomers = numCustomers;
						this.servicePoints = new Vector<Queue<Customer>>(); //a vector of service points, the line of each of which is represented by a queue of Customer objects
						this.customerList = new Vector<Customer>();
						//for each service point (ie for each line)
						for(int i = 0; i<numServicePoints; i++) {
							this.servicePoints.add(new QueueList<Customer>()); //initialize the given line as a Queue of customers
						}
						System.out.println("about to generate sequence");
						this.eventQueue = generateCustomerSequence(numCustomers, maxEventStart, seed);


						 //generate the queue of events. an event is a customer entering the store at a specific time
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
		PriorityQueue<Customer> line = new VectorHeap<Customer>(); //will store the preloaded 'line of customers' to enter the stored
			//using a type of PriorityQueue (here, VectorHeap) because it can remove values in increasing order, which is exactly what we need for customers arriving at a store
		//for each customer-to-be-generated
		for(int i=0; i<numCustomers; i++) {
			//add new customer
			Customer temp =
			new Customer(
				(int)(rnd.nextDouble() * maxEventStart),
				(int)(rnd.nextDouble() * (MAX_SERVICE_TIME - MIN_SERVICE_TIME)) + MIN_SERVICE_TIME
			);
			line.add(temp);
			getCustomerList().add(temp);
		}
		return line;
	}


	/**
	 * Does what is unique to the specific setup of the store
	 *
	 * @post the simulation has advanced 1 time step
	 * @return true if the simulation is over, false otherwise
	 */
	abstract public void unique(int i);
	abstract public void add(Customer c);


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

	public static void main(String[] args) {
		boolean multi=false;
		int numCustomers = 10; //number of customers
    int numServicePoints = 2; //number of service points (i.e. lines)
    int maxEventStart = 8; //last time a customer can enter a store
    int seed = 0; //for the Random generator

		//Processing arguments.
    Assert.pre(args.length>0, "Business Simulation main method requires at least one argument (indicating whether to simulate one service point or multiple service points.");
		//look at first argument, indicates whether multi or one Queue
			//give some flexibility to user by only requiring input to contain the first letter (One queue might also be called Single queue)
		String type = args[0].toLowerCase();
		if(type.contains("m")) multi=true;
		else if(type.contains("s") || type.contains("o")) multi=false;
		else Assert.fail("need to indicate either 'multi' or 'one' as first argument of the command line");

		//if user chooses to include the rest of the necessary arguments
    if(args.length>1){
      Assert.pre(args.length==5, "Either input only one argument ('multi' or 'one') or you also input the other four arguments for the simulation to override the defaults: \n1) the number of customers, \n2) the number of service points, \n3) the last time at which you'd like the costumers to arrive at the store, \n4) seed (for Random).");
      try{
        numCustomers = Integer.parseInt(args[1]);
        numServicePoints = Integer.parseInt(args[2]);
        maxEventStart = Integer.parseInt(args[3]);
        seed = Integer.parseInt(args[4]);
      }
      catch(Exception e){
        System.out.println("At least one four numbers input in command line cannot be rendered as an int");
      }
    }
    //System.out.println("int numCustomers, int numServicePoints, int maxEventStart are: " + numCustomers + ", " + numServicePoints + ", " + maxEventStart);

		//Instantiate a Queue
		BusinessSimulation bs;
		if(multi) bs = new MultiQueue(numCustomers, numServicePoints, maxEventStart, seed);
		else bs = new OneQueue(numCustomers, numServicePoints, maxEventStart, seed);
    //Print multiqueue for every time step
    do{
       //mq.print();
    } while(step(bs));

		String output;
		if(multi) output = "Multiple Service-Point";
		else output = "Single Line";

		//finding average wait time
		int sum = 0;
		for(int i = 0; i<getCustomerList().size(); i++){
			sum+=getCustomerList().get(i).getWaitTime();
		}
		int ave= sum/numCustomers;

	  System.out.println(output + " Simulation with " + numCustomers + " customers, " + numServicePoints + " lines, and time " + maxEventStart + " as the last time a customer can enter before store closes. \nTime = " + bs.getTime() + ". \nAverage wait time = " + ave + ".");
	}
	/**
	 * Advances 1 time step through the simulation.
	 *
	 * @post the simulation has advanced 1 time step
	 * @return true if the simulation is over, false otherwise
	 */
	public static boolean step(BusinessSimulation bs) {
    Customer temp;
    boolean hasNext;

    //while earliest person is entering now
    // System.out.println("event time of current first customer: " +  eventQueue.getFirst().getEventTime());
    while (!bs.eventQueue.isEmpty() && bs.eventQueue.getFirst().getEventTime()==bs.getTime()){
      // System.out.println("in while loop, event time of current guy being examined: "+ eventQueue.getFirst().getEventTime());
      //Add the given customer to the event queue, using add() method internal to this class,
        //simultaneously remove that customer from the eventQueue
      // System.out.println("adding next person on event cue: " + eventQueue.getFirst().getEventTime() + ", servicetime: " + eventQueue.getFirst().getServiceTime());
      bs.add(bs.getEventQueue().remove());
    };
    hasNext = false;

		//for every line of customers (ie for every service point)
		for(int i = 0; i<bs.getServicePoints().size(); i++){
			//if the line is not empty
      if(!bs.getServicePoints().get(i).isEmpty()){
				bs.unique(i); //run operations unique to specific type of simulation
				//if all line is still non-empty
        if(!bs.getServicePoints().get(i).isEmpty()) hasNext = true;
			}
		}
		//if all lines are empty
    if(!hasNext && bs.getEventQueue().isEmpty()){
      System.out.println("returning false at " + bs.getTime());
      return false;
    }
    bs.incrementTime(); //increment time
    return true; //indicates that business simulation should continue (because there are potentially more customers that will need to be served)
	}
	public PriorityQueue<Customer> getEventQueue(){
		return eventQueue;
	}
	public Vector<Queue<Customer>> getServicePoints(){
		return servicePoints;
	}
	public int getTime(){
		return time;
	}
	public int getSeed(){
		return seed;
	}
	public int getWaitTime(){
    return wait_time;
  }
	public int [] getStartTimes(){
		return start_times;
	}
	public int getNumServicePoints(){
		return numServicePoints;
	}
	public void incrementTime(){
		time++;
	}
	public int getNumCustomers(){
		return numCustomers;
	}
	public static Vector<Customer> getCustomerList(){
		return customerList;
	}
}
