# CuriousHungryRobot
A project from COMP182 Data structures and program design. Simulates a robot moving around a 2-D plane and capturing energy points randomly generated within this plane. The simulation uses 1000 trials using a stack (LIFO) and a queue as memory (FIFO) with the robot "consuming" the energy based on these data structures. 
# Findings
The findings show the robot moves more on average when it uses a stack as memory which makes sense since it consumes the closest (the last energy point in its memory) rather than the queue which will go to the first energy recorded in the queue. 
# To run 
`javac -cp . *.java` `java -cp . CuriousHungryRobot`
# Example output 

Robot Data Using Stack:
 SIZE = 1000| MIN = 400.387 | MAX = 5511.196 | MEAN = 1710.883 | MEDIAN = 1542.473 | SD = 983.264
 
Robot Data Using Queue:
 SIZE = 1000 | MIN = 400.387 | MAX = 4017.186 | MEAN = 679.802 | MEDIAN = 423.503 | SD = 450.075
