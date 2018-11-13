import structure5.*;
/**Class is meant to provide specifics of simulation for multiple-lines in a store.
  * In particular, has the necessary abstract methods: unique, add; and has a Constructor
  * @author Davey Morse Stephen Willis
  */
public class MultiQueue extends BusinessSimulation {
  /**
    *Constructor for MultiQueue
    * @param numCustomers the number of customers to be randomly generated
    * @param numServicePoints the number of lines in the store
    * @param maxEventStart the last time at which we want one of the randomly generated customers to be able to enter the store
    * @param seed for making sure the same random numbers are generated
    */
  public MultiQueue(int numCustomers, int numServicePoints, int maxEventStart, int seed) {
      super(numCustomers, numServicePoints, maxEventStart, seed);
      //for each service point (ie for each line)
      for(int i = 0; i<numServicePoints; i++) {
        this.getServicePoints().add(new QueueList<Customer>()); //initialize the given line as a Queue of customers
      }
  }
  /**
    * Performs the function unique to simulation of a store with multiple lines
    * post: does two things.
      * removes any customers from the front of lines if they've been fully served
      * if tellers are free, removed customers from lines to be served
    * @param i the line number that Business Simulation is currently looking at
    */
  public boolean unique(int i){
    Queue<Customer> line = servicePoints.get(i);
    //Remove a customer from line if they've been fully served
    //if that customer has been served fully:
        //(Note: to determine this, need to keep when this customer started being served, which is when the last customer's service ended. so will keep an array of temp times that each of the first people in lines' service started)
        //if served = if difference between current time and start of service time is the required time needed for the customer in question to be fully served:
    if (getTime()-line.getFirst().getServiceBegins()>=line.getFirst().getServiceTime()){
      line.remove(); //remove them from line if they've been served
      //Record start time for the new first-person in the line
      //if the line has at least one more customer
      if(line.size()>0){
        line.getFirst().setServiceBegins(getTime());
        line.getFirst().setWaitTime();
      }
    }
    if(line.size()>0) return true;
    else return false;
  }

  /**
  * Adds given customer to smallest line
  *
  *
  *
  */
  public void add(Customer c) {
    //First assume shortest line is the first
    int min = servicePoints.get(0).size();
    int best=0;
    //for every service line
    for(int i = 0; i<servicePoints.size(); i++) {
      //if the length is less than the previously recorded shortest line
      if(servicePoints.get(i).size()<min) {
        //record this service line as the new shortest line
        min=servicePoints.get(i).size();
        best=i;
      }
    }
    //add the customer c to this line.
      //but first, determine whether they are the first to be added to this line. we have to indicate when their services will begin:
      if(min == 0){
        c.setServiceBegins(getTime());
      } //Otherwise, start time is set when person in front of them in line finishes with their services
    servicePoints.get(best).add(c);
  }
}
