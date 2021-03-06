# Lab 8 High-level Feedback
 * Very nice work!
 * You could have moved even more functionality into the BusinessSimulation class (-1)

__Lab 8 Score: 25/26; Lab: 21/22; Questions: 4/4__
__Reviewed by Bill L__

We are the sole authors of all codes in this the classes: BusinessSimulation, Customer, OneQueue, MultiQueue.
# Lab 8: Waiting in Line
* BusinessSimulation is the central (abstract) class of the store simulation. It contains all of the code common to both types of simulations. Its main method runs the simulation, its step method increments time and calls on the necessary functions that are unique to each kind of simulations
* MultiQueue and OneQueue contain the methods necessary to simulate a store with multiple lines and a teller per each and a store with a single line where all the tellers are, respectively. They each have a unique() method, which deals with moving customers from waiting in line to being served by a teller, and deals with removing customers after they've been fully served, and a add() method, which moves customers from the pre-generated list to the line(s) in the store.
* Customer is the class that contains the necessary characteristics of a customer: a time at which they enter the store (eventTime), a length of time necessary for them to be fully served (serviceTime), as well as the time at which they begin to be served (serviceBegins) and a time length for which they waited in line before being served (waitTime). waitTime is the difference in time between when their serviceBegins and when the entered the store.



## Useful Links
 * [Course Homepage](http://cs.williams.edu/~cs136/index.html) (with TA schedule)
 * [Lab Webpage](http://cs.williams.edu/~cs136/labs/simulation.html)
 * [structure5](http://www.cs.williams.edu/~bailey/JavaStructures/doc/structure5/index.html) documentation
 * [GitHub markdown](https://guides.github.com/features/mastering-markdown/) syntax


## Thought Questions
 1. Run several simulations of both types of queues. Which queue strategy seems to process all the customers fastest? (_Note: For your simulations to be meaningful, they should represent a realistic situation. Customers should not overwhelm the tellers, and tellers should not be completely idle. When answering this thought question, give and justify the parameters you chose for your simulation._)
  * OneQueue seems to run faster, which makes sense because the tellers in a single-line simulation are never waiting when there are customers in line.
  * The parameters we chose was for the service time of each customer to vary between 5 and 20. So, assuming each unit of time is a minute makes sense, at a repair shop, say, might have a repair that takes five minutes or one that takes 20. If a store is open for about 20 hours in a given day, it is open for about 1200 minutes. We might guess that 300 customers might enter a store in a given day. Then, having eight tellers (and possibly eight lines) might also make sense. So, our input was the following: java BusinessSimulation o [or m] 300 8 1200 0. That result yields an average wait time of 1192 minutes for the OneQueue simulation and 1238 minutes for the MultiQueue simulation, a difference of just under an hour.
 2. Is there a difference between the average wait time for customers between the two techniques? (_A Customer's wait time is the difference between the time that a Customer appears in the simulation and the time that the Customer first begins receiving service from a teller. The wait time should not include the time that a Customer is actively receiving service._) Give the intuition for your results.
  * There is a difference in wait-time: customers in a store with multiple lines tend to wait longer. This difference is exaggerated when there are more customers, when those customer have longer-services needs, or when there are fewer tellers. As mentioned above, this makes sense because tellers in the multiple-line simulation might be idling while there are still customers to be served in other lines.
 3. Suppose you simulated the ability to jump between lines in a multiple line simulation. When a line has two or more customers than another line, customers move from the end of one line to another until the lines are fairly even. You see this behavior frequently at grocery stores. Does this change the type of underlying structure you use to keep customers in line?
  * Yes, this revision would change the data structure. We are currently using a Vector of Queues for a multiple-line simulation, but a queue only allows us to remove customers from the front of the line, not from any other index in a line. To take the last customers and move them to a different line, we would need a data structure that allows us to retrieve those customers--like a vector or linked-list.
 4. Suppose lines were dedicated to serving customers of varying lengths of service times. Would this improve the average wait time for a customer? If yes, how? If not, why not?
  * This would actually make the wait time even longer.  Imagine the two extremes: For the One-Queue, if all the customers on a day had short service times, the tellers for long service times would not be used.  The opposite is true as well.  For-Multi Queue, imagine the priority lines at a grocery store, something like '12 items or less'.  But if no customers have that few groceries, then the lines are just going to waste.  These extremes are not likely to happen, but they help illustrate the more small scale phenomena.  Because each customer has a different service time, and various customer are arriving throughout the day, there are bound to be periods with customers of only low service times or vice verse.  In this case, the teller would be idle.  In our current simulation, OneQueue never has an idle teller when a customer needs be served, thus this would strictly be worse performance.  On the other hand for multiQueue, we already sometimes have idle tellers who ideally should not be idle, but this would exacerbate the problem by explicitly making some tellers idle.
