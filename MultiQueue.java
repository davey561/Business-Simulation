import structure5.*;
/**Class is meant to provide specifics of simulation for multiple-lines in a store.
  *
  */
public class MultiQueue extends BusinessSimulation {

  //keep track of when each line's first customer started being served.
  int [] start_times; //Each index corresponds to a service point; each int is the time at which the customer currently being served in that line started being served
  int wait_time; //the sum of all wait times of the customers, to be divided by the total number of customers before returned

  /**
    * Main method runs MultiQueue simulation
    *
    *
    *
    */
  public static void main(String[] args) {
    int numCustomers = 10; //number of customers
    int numServicePoints = 2; //number of service points (i.e. lines)
    int maxEventStart = 8; //last time a customer can enter a store
    int seed = 0; //for the Random generator

    //Read in arguments from command line (String [] args)
    if(args.length>0){
      Assert.pre(args.length==4, "Constructor takes: int numCustomers, int numServicePoints, int maxEventStart, int seed");
      try{
        numCustomers = Integer.parseInt(args[0]);
        numServicePoints = Integer.parseInt(args[1]);
        maxEventStart = Integer.parseInt(args[2]);
        seed = Integer.parseInt(args[3]);
      }
      catch(Exception e){
        System.out.println("At least one four numbers input in command line cannot be rendered as an int");
      }
    }

    System.out.println("int numCustomers, int numServicePoints, int maxEventStart are: " + numCustomers + ", " + numServicePoints + ", " + maxEventStart);

    //Instantiate a MultiQueue
    MultiQueue mq= new MultiQueue(numCustomers, numServicePoints, maxEventStart, seed);
    //Print multiqueue for every time step
    do{
       mq.print();
    } while(mq.step());

    System.out.println("Multiple Service Point Simulation with " + numCustomers + " customers, " + numServicePoints + " lines, and time " + maxEventStart + " as the last time a customer can enter before store closes. \nTime = " + mq.getTime() + ". \nAverage wait time = " + (mq.getWaitTime())/numCustomers + ".");
  }


  public MultiQueue(int numCustomers, int numServicePoints, int maxEventStart, int seed) {
      super(numCustomers, numServicePoints, maxEventStart, seed);
      start_times = new int[numServicePoints];
      wait_time = 0;
  }
  /**
  * Increments time and simulates what will happen in this next step of service-customer business
  * pre:
  * post: adds all newly arriving customers to appropriate lines
  * @return true if the event queue is empty (if no more customers are arriving); false if there are potentially more customers coming
  */
  public boolean step() {
    Customer temp;
    boolean hasNext;
    //System.out.println("In step function.");
    //if there aren't any more customers, return true to abort business simulation

    //while earliest person is entering now
    // System.out.println("event time of current first customer: " +  eventQueue.getFirst().getEventTime());
    while (!eventQueue.isEmpty() && eventQueue.getFirst().getEventTime()==time){
      // System.out.println("in while loop, event time of current guy being examined: "+ eventQueue.getFirst().getEventTime());
      //Add the given customer to the event queue, using add() method internal to this class,
        //simultaneously remove that customer from the eventQueue

      // System.out.println("adding next person on event cue: " + eventQueue.getFirst().getEventTime() + ", servicetime: " + eventQueue.getFirst().getServiceTime());

      add(eventQueue.remove());
    };

    hasNext = false;
    //for every line of customers (ie for every service point)
    for(int i = 0; i<servicePoints.size(); i++){
      //if the line is not empty
      if(!servicePoints.get(i).isEmpty()){
        temp = servicePoints.get(i).get(); //get the first customer
        //if that customer has been served fully
          //to determine this, need to keep when this customer started being served, which is when the last customer's service ended. so will keep an array of temp times that each of the first people in lines' service started
        if(time-start_times[i]==temp.getServiceTime()){
          servicePoints.get(i).remove(); //remove them from line if they've been served
          //Indicate start time for the new first-person in the line
          wait_time += (start_times[i] - temp.getEventTime());
          start_times[i] = time; //start time of service for next customer is this time
        }
        //if all line is still non-empty
        if(!servicePoints.get(i).isEmpty()) hasNext = true;
      }
    }
    //if all lines are empty
    if(!hasNext){
      System.out.println("returning false at " + time);
      return false;
    }

    time++; //increment time
    return true; //indicates that business simulation should continue (because there are potentially more customers that will need to be served)
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
        start_times[best] = c.getEventTime();
      } //Otherwise, start time is set when person in front of them in line finishes with their services
    servicePoints.get(best).add(c);
  }

  public int getWaitTime(){
    return wait_time;
  }
}
