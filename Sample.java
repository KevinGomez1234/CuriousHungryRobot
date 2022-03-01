/*
-- Sample stores the values and computes statistical data on the simulations
******
Use the simulation technique then generate it to put the values in this class and use them in the array list 

// going to create 0,1, or eventually 2 instances of Sample is Java 
    need to use the simulation and pass its values to the ArrayList <E> 
******
-- need a generic array list
-- compute and report statistics on the data 
-- compute statistics method
-- private double variables 
-- getter methods to return each statistic 
-- There are 2 distributional simulations 
    -- 1 using the mean
    -- 1 using the median
 * @author kevin_gomez
 */
import java.util.ArrayList;
import java.util.Collections;
public class Sample  
{
    //initializes the array list
    private ArrayList<Double> myArray;
    //name of simulation strat
    private final String name;
    private double sum,min,max,median,sampStDev,mean;
    
    //instantiate the arrayList
    public Sample (String name)
    {
        this.name = name;
        myArray = new ArrayList <Double>();
    }
    //public setter method
    public void addValuesToArray(double value)
    {
        myArray.add(value);
    }
    
    //public getter method to get index of arraylist
    public double getArrayValue(int index)
    {
        return myArray.get(index);
    }
    //public getter method to get size of arraylist
    public int getArraySize(){
        return myArray.size();
    }
  // mean, median , standard deviation, size, min, max 
    public void computeStats()
    {  
        //helps get the median
        int arrayMedianCounter;
        //helps get the standard deviation
        double sumOfSquares = 0, temp;
        
        //sorts the data from least to greatest
        Collections.sort(myArray);
        
        //gets the min
        this.min = myArray.get(0);
        
        //gets the max
        this.max = myArray.get(myArray.size()-1);
        
        //gets the sum of all the entire array 
        for (Double i : myArray)
        {
            sum += i;            
        }
        this.mean = sum/myArray.size();
        
        //gets the median if the number of elements in array is odd 
        if (myArray.size() % 2 == 1)
        {
            this.median = myArray.get(((myArray.size()-1)/2));
        }
        
        //if even this formula for median is used 
        else if (myArray.size() % 2 == 0)
        {
            //array median counter is type int which will round to the int. This will get us one of the numbers
            //we have to add for the median. 
            arrayMedianCounter = (myArray.size()-1)/2;
            temp = myArray.get(arrayMedianCounter);
            this.median = temp + myArray.get(arrayMedianCounter+1);
            this.median = this.median / 2;
        }
        
      // for stdev you need original value - mean, then Square difference, add those values, then divde by amount of values 
      for(Double e : myArray)
      {    
          sumOfSquares = sumOfSquares + Math.pow(e-this.mean, 2);
          this.sampStDev = Math.pow(sumOfSquares / myArray.size(), .5);
      }
    }
    
    
    public String toString()
    {
        return String.format("%s:\n SIZE = %d, MIN = %.3f, MAX = %.3f, MEAN = %.3f, MEDIAN = %.3f, SD = %.3f\n ", this.name, myArray.size(),this.min,this.max,this.mean,this.median,this.sampStDev);
    }
}