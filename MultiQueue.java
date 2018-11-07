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
  public boolean step() {
    while(eventQueue.getFirst().getEventTime()==time){
      add(eventQueue.remove());
    }
    //for every line
    for(int i = 0; i<servicePoints.size(); i++){
        //if that customer has been served fully
        if(time-servicePoints.get(i).getFirst().getEventTime()-servicePoints.get(i).getFirst().getServiceTime()==0){
          servicePoints.get(i).remove();
        }
      }
      time++;
      return false;
    }
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
