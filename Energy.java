/*
The Energy class generates a random point and starts off with an Energy initial capacity of 300.
    -Capacity 300 
    -Set Point within a 200 unit radius
    -Creating 1 Energy per class
    -Detection
    -Drainage
 * @author kevin_gomez
 */

import java.util.Random;
import java.awt.Point;
public class Energy  
{
    //Energy has a point
    private Point p = new Point();
    //Current Energy
    private double energy;
    //Check if we detected the point
    private boolean detected;
    public Energy()
    {
        //Energy starts at 300 
        this.energy = 300.0;
        //Create a point once we instantiate
        setPoint();
        //Not detected at first
        this.detected = false;
    }
    
    //Gets how much energy we currently have
    public double getEnergy()
    {
        return energy;
    }
    
    //Drain energy 
    public void drainEnergy(double drain)
    {  
        this.energy = this.energy - drain;
    }

    //Set a point energy 
    public void setPoint()
    {
        Random rand = new Random();
        final int planeEnergyRadius= 200;
        do
        {
           p.setLocation(rand.nextInt(planeEnergyRadius + 1 + planeEnergyRadius) - planeEnergyRadius, rand.nextInt(planeEnergyRadius + 1 + planeEnergyRadius) - planeEnergyRadius);
        } while(p.distance(0, 0)>planeEnergyRadius);
    }
    
    //Allows us to access point methods
    public Point getPoint()
    {
        return this.p;
    }
    //Sets true if energy is detected
    public void setDetected()
    {
        this.detected = true;
    }
    //Energy detection
    public boolean getDected()
    {
        return this.detected;
    }
    
}