/*
Curious Hungry Robot
Curious Hungry Robot Has-a: ArrayList(Energies), ArrayDeque (Stores Energies) (Stack)(Queue), Robot, Sample, Energy 
LIFO/FIFO Memory

 * @author kevin_gomez
 */

import java.util.ArrayList;
import java.util.ArrayDeque;

public class CuriousHungryRobot 
{
    private ArrayList<Energy> energies;
    //Robot Memory to hold Robot Memory
    private ArrayDeque<Energy> robotMemory = new ArrayDeque<Energy>();

    public static void main(String[] args) {
        int simulationTrials = 1000;
        Sample stackData = new Sample("Robot Data Using Stack");
        Sample queueData = new Sample("Robot Data Using Queue");
        double totalDistance;
        CuriousHungryRobot app = new CuriousHungryRobot();
        //26-54 Stack Data
        for (int i = 1; i <= simulationTrials; i++) 
        {  
            //Robot Reset after 1 Trial
            Robot robot = new Robot();
            //Sets Energies: 33 with 20 unit distance boundary from one another
            app.setEnergiesArray();
            //Do this while Robot is active
            while (robot.getActive() == true){
                //If Curious or Hungry with an Empty Memory
                if (robot.getCurious() == true || (robot.getHungry() == true && app.robotMemory.isEmpty())) {
                    //Move to Curious Goal
                    robot.robotMove(robot.getCuriousGoalPoint());
                    //Check if we are close to an Energy, if we are store in memory Stack
                    app.robotMemoryStack(robot);
                    //If we reach Curious Goal reset curious goal
                    if (robot.getRobotPoint().distance(robot.getCuriousGoalPoint()) == 0) {
                        robot.setCuriousGoalPoint();
                    }
                    //If we are Hungry and Robot Memory stack is not Empty do this.
                } else if (robot.getHungry() == true && !app.robotMemory.isEmpty()) {
                    //Get first Element in stack.
                    Energy memoryGoal = app.robotMemory.peekFirst();
                    //Move to Memory Goal until we are inactive or we reach the memory goal.
                    while (robot.getActive() == true) {
                        robot.robotMove(memoryGoal.getPoint());
                        //Store in memory points we may encounter
                        app.robotMemoryStack(robot);
                        //If we reach memory goal drain Energy from Point, eat Energy, and set Robot State
                        if (robot.getRobotPoint().distance(memoryGoal.getPoint()) == 0) {
                            app.drainEnergyFromFirst(robot);
                            robot.setRobotState();
                            break;
                        }
                    }
                }
            }//at the end of trial store the TotalDistance Traveled in Sample.
            totalDistance = robot.getTotalDistanceTraveled();
            stackData.addValuesToArray(totalDistance);
        }
        //Compute stats, and show results
        stackData.computeStats();
        System.out.println(stackData);
        //56-84 Queue Data, only differece is Queue Method
        for (int i = 1; i <= simulationTrials; i++) 
        {
            Robot robot = new Robot();
            app.setEnergiesArray();
            while (robot.getActive() == true){
                if (robot.getCurious() == true || (robot.getHungry() == true && app.robotMemory.isEmpty())) {
                    robot.robotMove(robot.getCuriousGoalPoint());
                    app.robotMemoryQueue(robot);
                    if (robot.getRobotPoint().distance(robot.getCuriousGoalPoint()) == 0) {
                        robot.setCuriousGoalPoint();
                    }
                } else if (robot.getHungry() == true && !app.robotMemory.isEmpty()) {
                    Energy memoryGoal = app.robotMemory.peekFirst();
                    while (robot.getActive() == true) {
                        robot.robotMove(memoryGoal.getPoint());
                        app.robotMemoryQueue(robot);
                        if (robot.getRobotPoint().distance(memoryGoal.getPoint()) == 0) {
                            app.drainEnergyFromFirst(robot);
                            robot.setRobotState();
                            break;
                        }
                    }
                }
            }
            totalDistance = robot.getTotalDistanceTraveled();
            queueData.addValuesToArray(totalDistance);
        }
        queueData.computeStats();
        System.out.println(queueData);

    }

    //Robot Memory Using Queue addLast().
    public void robotMemoryQueue(Robot r)
    {
        int detectionRadius = 13;
        for (int i = 0; i <= this.energies.size() - 1; i++) {
            if (this.energies.get(i).getPoint().distance(r.getRobotPoint().getLocation()) <= detectionRadius && this.energies.get(i).getDected() == false) {
                this.robotMemory.addLast(this.energies.get(i));
                this.energies.get(i).setDetected();
            }
        }
    }

    //Checks if Robot is within 13 units from a Energy point. If it is add it to Robot memory remember we stored it by activating detected.
    public void robotMemoryStack(Robot r) 
    {
        int detectionRadius = 13;
        for (int i = 0; i <= this.energies.size() - 1; i++) {   //Check distance boundary of every Energy in Arraylist to current Robot Location and Check if the Point has been detected Already
            if (this.energies.get(i).getPoint().distance(r.getRobotPoint().getLocation()) <= detectionRadius && this.energies.get(i).getDected() == false) {
                //add to the stack
                this.robotMemory.addFirst(this.energies.get(i));
                //Energy in ArrayList is now set as detected. We will use the point until we finish eating from it so we do not have to detect again.
                this.energies.get(i).setDetected();
            }
        }
    }

    //Drains Energy from the first element in Stack or Queue. If all energy is drained remove the point from memory, else keep it for next time Robot is hungry.
    public void drainEnergyFromFirst(Robot r) 
    {
        final double MAX_ROBOT_ENERGY = 400.0;
        //Max amount robot can eat. 
        double maxAmountToEat = MAX_ROBOT_ENERGY - r.getRobotEnergy();
        // Energy at the top of the stack - maximum amount of energy the Robot can eat
        double difference = this.robotMemory.peekFirst().getEnergy() - maxAmountToEat;
        //Robot eats everything in the point so we remove the most recent point from the stack.
        if (difference <= 0) {
            r.eatEnergy(this.robotMemory.removeFirst().getEnergy());
        } //robot just eats some of the energy, so we do not remove point from memory. 
        else if (difference > 0) {
            r.eatEnergy(maxAmountToEat);
            this.robotMemory.getFirst().drainEnergy(maxAmountToEat);
        }
    }

    //Creates 33 Energies with a 20 unit radius boundary and puts them into an ArrayList 
    public void setEnergiesArray() 
    {
        //New ArrayList every time we run this method
        this.energies = new ArrayList<Energy>();
        //Constants
        final int ENERGIES = 33;
        final int DISTANCE_BOUNDARY = 20;
        //create 33 Energy instances and put them into an ArrayList before creating a new one.
        for (int i = 1; i <= ENERGIES; i++) {
            Energy energy = new Energy();
            this.energies.add(energy);
        }
        // Spreads out the Points. With a distance boundary of 20. 
        for (int j = 0; j <= this.energies.size() - 1; j++) {   //Check current point to all other points
            for (int k = 0; k <= this.energies.size() - 1; k++) {   //Push point away from current point if it is too close.
                while (j != k && this.energies.get(j).getPoint().distance(this.energies.get(k).getPoint()) <= DISTANCE_BOUNDARY) {
                    this.energies.get(k).setPoint();
                }
            }
        }
    }
}
