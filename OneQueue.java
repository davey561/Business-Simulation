import structure5.*;

/**
  * Contains specifics of code for one-line store in business simulation.
  * In particular, has the necessary abstract methods (add() and unique()) and a constructor
  * @author Davey Morse and Stephen Willis
  */
public class OneQueue extends BusinessSimulation {
  Vector<Customer> being_served = new Vector <Customer>(); //keeps track of customers currently being served

  /**
    * Constructor for onequeue
    * @param getNumCustomers the number of customers in the simulation
    * @param numServicePoints the number of services points (i.e. lines) in the simulation--in this onequeue setup, there should only be one
    * @param maxEventStart the last time at which we want one of the randomly generated customers to be able to enter the store
    * @param seed for making sure the same random numbers are generated
    */
  public OneQueue(int numCustomers, int numServicePoints, int maxEventStart, int seed) {
      super(numCustomers, numServicePoints, maxEventStart, seed);
  }
  /**
    * Performs the actions that are unique to a simulation of a store with a single line
    * pre: eventQueue and servicePoints are initialized with customers
    * post: does two things.
      * removes any customers from being served in they've been being served for the amount of service time they needed
      * if some tellers are free, removed customers from the line to be served
    * @param unnecessary_param unnecessary parameter, but we think it's required because the abstract method unique() in BusinessSimulation (the parent class) has this as a parameter
    */
  public void unique(int unnecessary_param){
    boolean teller_available; //whether any teller is available
    Customer temp; //temporarily stores particular customers
    Queue <Customer> line = servicePoints.get(0); //the line of customers inside store

    //Check to see if any of customers being served is fully served. If so, remove them.
    //for every customer being served
    int size = being_served.size();
    for(int i = 0; i<size; i++){
      temp = being_served.get(0);
      //System.out.println("38" + temp.toString());
      //if they've been "being served" for enough time
      if(getTime() - temp.getServiceBegins()>=temp.getServiceTime()){
        being_served.remove(temp); //remove them from the being_served vector, making space for new customers to be served
        //System.out.println(temp.toString() + "just removed, " + being_served.size());
      }
    }
    //Update list of customers being served
    //if there are tellers waiting, add customers
    while(being_served.size()<getNumServicePoints() && (line.size()>0)){
      temp = line.remove();
      being_served.add(temp);
      temp.setServiceBegins(getTime());
      temp.setWaitTime(); //record how long they waited
    }
  }

  /**
  * Adds given customer to smallest line
  * @param c the customer to be added
  * post: adds c to line
  */
  public void add(Customer c) {
    servicePoints.get(0).add(c);
  }
}
