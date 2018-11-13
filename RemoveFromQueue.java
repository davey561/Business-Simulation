import structure5.*;
public class RemoveFromQueue{
  public RemoveFromQueue (){
    super();
  }
  public Customer remove(Queue<Customer> q, Customer val){
    QueueList<Customer> tempq = new QueueList<Customer>();
    //pop until reaching matching customer
    Customer c1;
    while(!q.getFirst().equals(val)){
      c1 = q.remove();
      System.out.println("Removing customer " + c1.toString());
      tempq.add(c1);
    }
    // System.out.println("14");
    // boolean k = q.getFirst().equals(val);
    Assert.pre(!q.isEmpty() /*&& q.getFirst().equals(val)*/, "customer in stack should be equal to the desired one to be removed but isnt. customer in stack: ");
    Customer temp = q.remove();
    System.out.println(temp.equals(val));
    int l = tempq.size();
    System.out.println(l);
    Customer c2;
    for(int i = 0; i<l; i++){
      c2 = tempq.remove();
      System.out.println("adding back " + c2.toString());
      q.add(c2);
    }
    return temp;
  }
  public static void main(String[] args) {
    QueueList<Customer> q = new QueueList<Customer>();
    q.add(new Customer (2, 1));
    Customer second = new Customer (4,2);
    q.add(second);
    q.add(new Customer (5,3));
    RemoveFromQueue r = new RemoveFromQueue();
    r.remove(q, second);
    System.out.println(q.toString());
  }
}
