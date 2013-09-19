/* Emily Segalini
 * emsegalini
 * September 13, 2013
 * -Percolation class takes an N*N grid and determines if there is a
 * connection from the top size to the bottom (i.e. persizeates).
 * -Execute using test program or PercolationStats.java.
 */

public class Percolation {

    private boolean[] site; //Stores true if site is open.
    private int size; //Number of sizes and sizeumns
    private WeightedQuickUnionUF location; //Aligns with grid.
    private WeightedQuickUnionUF backwash;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)             
   {  
       size = N+2;
       location = new WeightedQuickUnionUF(size*size);
       backwash = new WeightedQuickUnionUF((size*size));
       site = new boolean[size*size];
       
       site[xytoId(0, 0)] = true;         
       site[xytoId(size-1, size-1)] = true;
       
       for (int i = 0; i < size; i++)
       {
           site[xytoId(0, i)] = true;         
           site[xytoId(size-1, i)] = true;
           location.union(xytoId(0, i), xytoId(0, 0));
           backwash.union(xytoId(0, i), xytoId(0, 0));
           location.union(xytoId(size-1, i), xytoId(size-1, size-1));
       }
    }
       
   public void open(int i, int j)         
   {
       testBounds(i, j); 
       
       if (!isOpen(i, j))
       {
       site[xytoId(i, j)] = true;
       if (i-1 == 0) 
       {   location.union(xytoId(i, j), xytoId(i-1, j));
           backwash.union(xytoId(i, j), xytoId(i-1, j)); } 
       else if (isOpen(i-1, j)) 
       {    location.union(xytoId(i, j), xytoId(i-1, j));
            backwash.union(xytoId(i, j), xytoId(i-1, j)); } 
       if (i+1 == size-1)
           location.union(xytoId(i, j), xytoId(i+1, j));
        else if (i+1 < size-1 && isOpen(i+1, j)) 
        {   location.union(xytoId(i, j), xytoId(i+1, j));
            backwash.union(xytoId(i, j), xytoId(i+1, j)); } 
       if (j-1 > 0 && isOpen(i, j-1)) 
       {   location.union(xytoId(i, j), xytoId(i, j-1));
           backwash.union(xytoId(i, j), xytoId(i, j-1)); } 
       if (j+1 < size-1 && isOpen(i, j+1)) 
       {    location.union(xytoId(i, j), xytoId(i, j+1));
            backwash.union(xytoId(i, j), xytoId(i, j+1)); } 
       }
   }

   // is site (size i, sizeumn j) open?
   public boolean isOpen(int i, int j)    
   {
       testBounds(i, j);
       if (site[xytoId(i, j)]) return true;
       
       return false;
   }
       
    // is site (size i, sizeumn j) full?
   public boolean isFull(int i, int j)   
   {
       testBounds(i, j);
       if (backwash.connected(xytoId(i, j), xytoId(0, 0))) return true;

       return false;
   }
   
   // does the system persizeate?
   public boolean percolates()            
   {
       if (location.connected(xytoId(size-1, size-1), xytoId(0, 0)))
           return true;      
       return false;
   }
   private int xytoId(int x, int y)
   {
       if (x == 0 && y == 0) return 1;
       return (x*size) + y;
   }
   
   //Tests if block is within grid.
   private void testBounds(int x, int y)
   {
       if (x <= 0 || y >= size-1 || y <= 0 || x >= size-1) 
           throw new java.lang.IndexOutOfBoundsException();
   }
}