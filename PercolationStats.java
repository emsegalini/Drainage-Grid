/* Emily Segalini
 * emsegalini
 * September 13, 2013
 * -PercolationStats class runs the Monte Carlo simulation T times for an N*N grid.
 * -Execute using main method with input integers N and T.
 */

public class PercolationStats 
{
   private int[] experiments; //Stores threshold result for T runs.
   private Percolation grid; //Test percolation class.
   private double total;  //Total number of attempts made for all T runs.
   private int reps;  //Number of times run.
   private double size;
   
   // perform T independent computational experiments on an N-by-N grid
   public PercolationStats(int N, int T)    
   {   
       testArgs(N, T);
       
       int count; //Counts open blocks.
       int x, y; //Random int holders
       size = N;
       experiments = new int[T];
       reps = T;
       
       for (int i = 0; i < T; i++)
       {   grid = new Percolation(N);
           count = 0; //resets count to 0
           
           while (!grid.percolates())
           {   x = StdRandom.uniform(N)+1;
               y = StdRandom.uniform(N)+1;
               if (!grid.isOpen(x, y))
               {    grid.open(x, y);
                   count++;
               }
           }
           experiments[i] = count;
           total += count/(size*size);
       }
   }    
   
   // sample mean of percolation threshold
   public double mean()
   {    return total/reps;
   }

   // sample standard deviation of percolation threshold
   public double stddev()  
   {    
        double sum = 0;  
        
        for (int i = 0; i < reps; i++)
        {
            sum += (experiments[i]/(size*size)-mean())
                *(experiments[i]/(size*size)-mean());
        }
        return Math.sqrt(sum/reps);
   }
   
   // returns lower bound of the 95% confidence interval
   public double confidenceLo()  
   {
       return mean()-((1.96 * stddev())/Math.sqrt(reps));
   }
   
   // returns upper bound of the 95% confidence interval
   public double confidenceHi()             
   {
       return mean()+((1.96 * stddev())/Math.sqrt(reps));
   }

    // test client, described below   
   public static void main(String[] args)   
   {
       int n = StdIn.readInt();
       int t = StdIn.readInt();
       PercolationStats stats = new PercolationStats(n, t);
       
       StdOut.println("mean = " + stats.mean());
       StdOut.println("stddev = " + stats.stddev());
       StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " 
                          + stats.confidenceHi());
   }
   
   //Tests that N is greater than 0 and T greater than 1
   private void testArgs(int n, int t)
   {
       if (n <= 0 || t <= 0) throw new java.lang.IllegalArgumentException();
       if (t == 1) throw new java.lang.IllegalArgumentException("Not enough " 
                                    + "iterations. T must be higher than 1.");
   }
}