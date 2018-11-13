Incomplete assignment, late day requested.
# Lab 8: Waiting in Line

## Useful Links
 * [Course Homepage](http://cs.williams.edu/~cs136/index.html) (with TA schedule)
 * [Lab Webpage](http://cs.williams.edu/~cs136/labs/simulation.html)
 * [structure5](http://www.cs.williams.edu/~bailey/JavaStructures/doc/structure5/index.html) documentation
 * [GitHub markdown](https://guides.github.com/features/mastering-markdown/) syntax


## Thought Questions
 1. Run several simulations of both types of queues. Which queue strategy seems to process all the customers fastest? (_Note: For your simulations to be meaningful, they should represent a realistic situation. Customers should not overwhelm the tellers, and tellers should not be completely idle. When answering this thought question, give and justify the parameters you chose for your simulation._)
  * OneQueue seems to run faster, which makes sense because the tellers in a single-line simulation are never waiting when there are customers in line.
 2. Is their a difference between the average wait time for customers between the two techniques? (_A Customer's wait time is the difference between the time that a Customer appears in the simulation and the time that the Customer first begins receiving service from a teller. The wait time should not include the time that a Customer is actively receiving service._) Give the intuition for your results.
  * There is a difference in wait-time: customers in a store with multiple lines tend to wait longer. This difference is exaggerated when there are more customers, when those customer have longer-services needs, or when there are fewer tellers. As mentioned above, this makes sense because tellers in the multiple-line simulation might be idling while there are still customers to be served in other lines.
 3. Suppose you simulated the ability to jump between lines in a multiple line simulation. When a line has two or more customers than another line, customers move from the end of one line to another until the lines are fairly even. You see this behavior frequently at grocery stores. Does this change the type of underlying structure you use to keep customers in line?
  * Yes, this revision would change the data structure. We are currently using a Vector of Queues for a multiple-line simulation, but a queue only allows us to remove customers from the front of the line, not from any other index in a line. To take the last customers and move them to a different line, we would need a data structure that allows us to retrieve those customers--like a vector or linked-list.
 4. Suppose lines were dedicated to serving customers of varying lengths of service times. Would this improve the average wait time for a customer? If yes, how? If not, why not?
  * Your answer here.
