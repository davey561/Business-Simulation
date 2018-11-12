import structure5.*;
/**Class is meant to provide specifics of simulation for multiple-lines in a store.
  *
  */
public class MultiQueue extends BusinessSimulation {
  public MultiQueue(int numCustomers, int numServicePoints, int maxEventStart, int seed) {
      super(numCustomers, numServicePoints, maxEventStart, seed);
  }
  public void unique(int i){
    Customer temp = servicePoints.get(i).get(); //get the first customer
    //if that customer has been served fully
      //to determine this, need to keep when this customer started being served, which is when the last customer's service ended. so will keep an array of temp times that each of the first people in lines' service started
    if(getTime()-temp.getServiceBegins()>=temp.getServiceTime()){
      servicePoints.get(i).remove(); //remove them from line if they've been served
      //Indicate start time for the new first-person in the line
      temp.setServiceBegins(getTime());
    //  temp.setWaitTime(); System.out.println("wait time: " + temp.getWaitTime());
    }
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
