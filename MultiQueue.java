import structure5.*;

public class MultiQueue extends BusinessSimulation {


  public static void main(String[] args) {

    MultiQueue store;

    if(args.length==0)

      store= new MultiQueue(100, 4, 300, 0);

    else {

      Assert.pre(args.length==4, "Constructor takes: int numCustomers, int numServicePoints, int maxEventStart, int seed");

    store = new MultiQueue(Integer.parseInt(args[0]), Integer.parseInt(args[1]),

    Integer.parseInt(args[2]), Integer.parseInt(args[3]));

    }

  }


  public MultiQueue(int numCustomers, int numServicePoints,

        int maxEventStart, int seed) {

        super(numCustomers, numServicePoints, maxEventStart, seed);

        }
  /**
  * Increments time and simulates what will happen in this next step of service-customer business
  * pre:
  * post: adds all newly arriving customers to appropriate lines
  * @return true if the event queue is empty (if no more customers are arriving); false if there are potentially more customers coming
  */
  public boolean step() {
    //if there aren't any more customers, return true to abort business simulation
    if(eventQueue.isEmpty()) return true;
    //while the person at front of line is entering now.
    while(eventQueue.getFirst().getEventTime()==time){
      //add the given customer to the event queue, using add() method internal to this class
      add(eventQueue.remove());
    }
    //for every line of customers
    for(int i = 0; i<servicePoints.size(); i++){
        //if that customer has been served fully
        if(time-servicePoints.get(i).getFirst().getEventTime()-servicePoints.get(i).getFirst().getServiceTime()==0){
          servicePoints.get(i).remove(); //remove them from line if they've been served
        }
      }
      time++; //increment time
      return false; //indicates that business simulation should continue (because there are potentially more customers that will need to be served)
    }
  /**
  * Adds given customer to smallest line
  *
  *
  *
  */
  public void add(Customer c) {
    int min=servicePoints.get(0).size();
    int best=0;
    for(int i=0; i<servicePoints.size(); i++) {
      if(servicePoints.get(i).size()<min) {
        min=servicePoints.get(i).size();
        best=i;
      }
    }
    servicePoints.get(best).add(c);
  }
}
