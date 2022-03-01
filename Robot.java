/*
The Robot class represents a moving Robot
    -Has states
        -START CURIOUS
        -NOT ACTIVE when out of energy 
        -curious when robotEnergy>MAXCAP
        -hungry when robotEnergy<=MAXCAP
    -Has a Point (Location)
    -Can drain Energy
    -Has a max capacity of 400 energy
    -Robot moves
        -After every move calculate how much it has moved
        -Moves diagonally(18.3), "straight" (13), or does not move at all
        -Snaps if within a certain distance
 * @author kevin_gomez
 */


//Robot has a point
import java.awt.Point;
//Robot has a Random
import java.util.Random;
public class Robot 
{
    //Generate a random curious goal
    private Random rand = new Random();
    //Current Robot Energy
    private double robotEnergy;
    //Curious state
    private boolean curious;
    //Hungry state
    private boolean hungry;
    //Robot's active state
    private boolean isActive;
    //Robot's position
    private Point robotPoint = new Point();
    //Robot's curious Goal
    private Point curiousGoal = new Point();
    //Robot's distance Traveled
    private double distanceTraveled;
    //Robot's total distance traveled
    private double totalDistanceTraveled;
    
    //Start off with a full tank, from origin, and start of curious
    public Robot()
    {
        this.distanceTraveled = 0;
        this.robotEnergy = 400.0;
        robotPoint.setLocation(0, 0);
        //curiousGoal
        setCuriousGoalPoint();
        this.curious = true;
        this.hungry = false;
        this.isActive = true;
    }
    
    //Takes in a point and moves towards that point. Also calls methods that drain energy, sets Robot state, sets total distance traveled
    public void robotMove(Point goalPoint)
    {
        //Makes a copy of the point before the move so we can calculate the distance traveled at the end of the move.
        int prevRobotX = this.robotPoint.x;
        int prevRobotY = this.robotPoint.y;

        final double moveDistance = 13.0;
        final double snapDistance = 9.0;
        //Used to Compare distance of each goal X or Y to get the distance to the Robot's x or y. 
        Point xComp = new Point();
        Point yComp = new Point();
        xComp.setLocation(goalPoint.x, 0);
        yComp.setLocation(0, goalPoint.y);

        //If the Robot is within Snap Distance Dont Move... 
        if(robotPoint.distance(goalPoint.x, goalPoint.y)<=snapDistance)
        {
           this.robotPoint.setLocation(this.robotPoint.x, this.robotPoint.y);
        }
        
        //Diagonal Moves
        else if (robotPoint.distance(goalPoint.x, goalPoint.y)>moveDistance && !(robotPoint.x == goalPoint.x) && !(robotPoint.y == goalPoint.y) )
        {
            if(robotPoint.x>goalPoint.x && robotPoint.y > goalPoint.y){
                robotPoint.setLocation(robotPoint.x - moveDistance, robotPoint.y - moveDistance);
            }
            else if (robotPoint.x>goalPoint.x && robotPoint.y<goalPoint.y){
                robotPoint.setLocation(robotPoint.x - moveDistance, robotPoint.y+moveDistance);
            }
            else if(robotPoint.x<goalPoint.x && robotPoint.y>goalPoint.y){
                robotPoint.setLocation(robotPoint.x+moveDistance, robotPoint.y-moveDistance);
            }
            else if (robotPoint.x<goalPoint.x && robotPoint.y<goalPoint.y){
                robotPoint.setLocation(robotPoint.x + moveDistance, robotPoint.y + moveDistance);
            }
        }
        //Short Moves
        else if(robotPoint.distance(goalPoint.x, goalPoint.y)<=moveDistance || robotPoint.x == goalPoint.x || robotPoint.y == goalPoint.y)
        {
            //Short moves when on x or y.
            if(robotPoint.x>goalPoint.x && robotPoint.y == goalPoint.y)
            {
                robotPoint.setLocation(robotPoint.x-moveDistance, robotPoint.y);
            }
            else if (robotPoint.y>goalPoint.y && robotPoint.x == goalPoint.x)
            {
                robotPoint.setLocation(robotPoint.x, robotPoint.y - moveDistance);
            }
            else if (robotPoint.y<goalPoint.y && robotPoint.x ==goalPoint.x)
            {
                robotPoint.setLocation(robotPoint.x, robotPoint.y + moveDistance);
            }
            else if (robotPoint.x<goalPoint.x && robotPoint.y == goalPoint.y)
            {
                robotPoint.setLocation(robotPoint.x + moveDistance, robotPoint.y);
            }
            
            //Short moves close to the point.
            else if(robotPoint.x<goalPoint.x && xComp.distance(robotPoint.x,0)>=yComp.distance(0, robotPoint.y))
            {
                robotPoint.setLocation(robotPoint.x + moveDistance, robotPoint.y);
            }
            else if (robotPoint.x>goalPoint.x && xComp.distance(robotPoint.x,0)>=yComp.distance(0, robotPoint.y))
            {
                robotPoint.setLocation(robotPoint.x-moveDistance, robotPoint.y);
            }
            else if (robotPoint.y<goalPoint.y && (yComp.distance(0, robotPoint.y)>=xComp.distance(robotPoint.x,0)))
            {
                robotPoint.setLocation(robotPoint.x, robotPoint.y + moveDistance);
            }
            else if (robotPoint.y>goalPoint.y && yComp.distance(0, robotPoint.y)>=xComp.distance(robotPoint.x,0))
            {
                robotPoint.setLocation(robotPoint.x, robotPoint.y - moveDistance);
            }
        }
        //DistanceTraveled for this move.
        this.distanceTraveled = this.robotPoint.distance(prevRobotX, prevRobotY); 
        //Drain Robot Energy for this move
        drainRobotEnergy();
        //Sets the Robots state for this move
        setRobotState();
        //Sets the total distance traveled 
        setTotalDistanceTraveled();

         // SNAP if we got within snap Distance.
        if(robotPoint.distance(goalPoint.x, goalPoint.y)<=snapDistance)
        {
           this.robotPoint.setLocation(goalPoint.x, goalPoint.y);
        }
    }
   //Drains the Robots energy after a move.
    public void drainRobotEnergy()
    {
        this.robotEnergy = this.robotEnergy - this.distanceTraveled;
    }
    //Gets the Robots energy.
    public double getRobotEnergy()
    {
        return this.robotEnergy;
    }
    //Gets the RobotPoint in Robot class
    public Point getRobotPoint()
    {
        return this.robotPoint;
    }
    //Sets the curious goal point
    public void setCuriousGoalPoint()
    {
        int planeEnergyRadius = 200;
        do
        {
            curiousGoal.setLocation(rand.nextInt(planeEnergyRadius + 1 + planeEnergyRadius) - planeEnergyRadius, rand.nextInt(planeEnergyRadius + 1 + planeEnergyRadius) - planeEnergyRadius );
        }while(curiousGoal.distance(0, 0)>planeEnergyRadius);
    }
    //Gets the curious goal point in Robot class
    public Point getCuriousGoalPoint()
    {
        return this.curiousGoal;
    }
    //Sets the Robots State 
    public void setRobotState()
    {
        final double ROBOT_MAXIMUM_CAPACITY = 400;
        //HUNGRY
        if(this.robotEnergy <= ROBOT_MAXIMUM_CAPACITY/2 && this.robotEnergy>0)
        {
            this.isActive = true;
            this.curious = false;
            this.hungry = true;
        }
        //CURIOUS
        else if(this.robotEnergy>ROBOT_MAXIMUM_CAPACITY/2)
        {
            this.isActive= true;
            this.curious = true;
            this.hungry = false;
        }
        //NOT ACTIVE, NOT CURIOUS,NOT HUNGRY
        else if(this.robotEnergy<=0)
        {
            this.curious = false;
            this.hungry = false;
            this.isActive = false;
        }
    }
    
    //Returns the Robot's curious state
    public boolean getCurious()
    {
        return this.curious;
    }
    //Returns the Robot's hungry state
    public boolean getHungry()
    {
        return this.hungry;
    }
    //Gets the distance traveled for the move and adds it to the total distance traveled
    public void setTotalDistanceTraveled()
    {
       double temp = this.distanceTraveled;
       this.totalDistanceTraveled = temp + totalDistanceTraveled;
    }
    //Gets the total distance the Robot traveled
    public double getTotalDistanceTraveled()
    {
        return this.totalDistanceTraveled;
    }
    //Eat energy allows us to put in a double and add it to the Energy
    public void eatEnergy(double energyConsumed)
    {
        this.robotEnergy = this.robotEnergy + energyConsumed;
    }
    //If Energy <= 0  isActive == false
    public boolean getActive()
    {   
        return this.isActive;
    }

}
