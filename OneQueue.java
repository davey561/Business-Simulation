import structure5.*;

/**
  * Contains specifics of code for one-line store in business simulation
  *
  *
  */
public class OneQueue extends BusinessSimulation {
  Vector<Customer> being_served = new Vector <Customer>(); //keeps track of customers currently being served

  /**
    * Constructor for onequeue
    *
    */
  public OneQueue(int numCustomers, int numServicePoints, int maxEventStart, int seed) {
      super(numCustomers, numServicePoints, maxEventStart, seed);
      start_times = new int[numServicePoints]; //initialize start times array--length is number of tellers
      //Customer [] being_served; //initializes being_served
  }
  /**
    * pre: eventQueue and servicePoints are initialized with customers
    *
    */
  public void unique(int unnecessary_param){
    Queue <Customer> line = servicePoints.get(0); //the line of customers inside store
    boolean teller_available; //whether a teller is available
    Customer temp; //temp customary
    Customer removed; //the customer being removed

    //Check to see if any of customers being served is fully served. If so, remove them.
    //for every customer being served
    for(int i = 0; i<being_served.size(); i++){
      temp = being_served.get(i); //look at this customer
      //if they've been "being served" for enough time
      if(time - temp.getServiceBegins()==temp.getServiceTime()){
        being_served.remove(temp);
      }
    }
    //Update list of customers being served
    //if there are tellers waiting, add customers
    while(being_served.size()<getNumServicePoints() && (line.size()>0)){
      removed = line.remove();
      being_served.add(removed);
      removed.setServiceBegins(getTime());
      removed.setWaitTime(); //record how long they waited
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
