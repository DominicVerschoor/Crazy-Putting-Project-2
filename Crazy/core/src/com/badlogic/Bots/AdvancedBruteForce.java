package com.badlogic.Bots;



import com.badlogic.FileHandling.FileReader;
import com.badlogic.PhyiscSolvers.Rk4;


import java.util.Random;

public class AdvancedBruteForce {
    // current position of the ball in x,y direction
    private double[] currentPositions;
    private static FileReader r = new FileReader();

    // our heuristic function that will determine where the ball's next position will be
    private double[] currentVelocities;

    public AdvancedBruteForce(double[] currentPositions, double[] currentVelocities) {
        this.currentPositions = currentPositions;
        this.currentVelocities = currentVelocities;
    }

    public void setPositions(double[] position) {
        currentPositions = position;
    }

    public double[] getAdvancedBot() {
        return currentPositions;
    }

    public void setVelocities(double[] velocities) {
        currentVelocities = velocities;
    }

    public double[] getVelocities() {
        return currentVelocities;
    }

    // calculate new position for the ball (randomly generated velocities for now)
    public AdvancedBruteForce findNextAdvancedBot(double[] startPosition, double[] goalPosition, double[] xbounds, double[] ybounds) {
        AdvancedBruteForce newPosition = new AdvancedBruteForce(startPosition, goalPosition);
        Random r = new Random();

        // if we want to have a lowerbound:
        // random = lowerbound + (upperbound - lowerbound) * r.nextDouble();
        //double lowerbound = 1;

        double randomXvelocity = xbounds[0] + (xbounds[1] - xbounds[0]) * r.nextDouble();
        double randomYvelocity = ybounds[0] + (ybounds[1] - ybounds[0]) * r.nextDouble();

        double[] velocities = {randomXvelocity, randomYvelocity};

        newPosition.setVelocities(velocities);

        double[] newArrXt = new double[4];

        newArrXt[0] = startPosition[0];
        newArrXt[1] = startPosition[1];
        newArrXt[2] = velocities[0];
        newArrXt[3] = velocities[1];

        Rk4 rk4 = new Rk4();
        double[] fillarray = rk4.solve(newArrXt);
        double[] newPos = {fillarray[0], fillarray[1]};
        newPosition.setPositions(newPos);


        return newPosition;
    }

    // comparing currentPosition to newly calculated Positn of previous method and setting new AdvancedBot
    public AdvancedBruteForce HillClimbingMethod(double[] startPosition, double[] goalPosition) {
        double[] xbounds = {-5, 5};
        double[] ybounds = {-5, 5};
        double[] currentPosition = startPosition;
        AdvancedBruteForce newPosition = new AdvancedBruteForce(currentPosition, goalPosition);
        int count = 0;
        int limitation = 0;

        while (distanceOfVectors(currentPosition, goalPosition) > 1 && limitation !=500)

        {
            //System.out.println("I am in the while loop - before the if AdvancedBotment.");
            newPosition = findNextAdvancedBot(startPosition, goalPosition, xbounds, ybounds);
            count++;

            if ((distanceOfVectors(currentPosition, goalPosition) >= distanceOfVectors(newPosition.getAdvancedBot(), goalPosition)) && (newPosition.getVelocities()[0] < 5 && newPosition.getVelocities()[0] > -5 && newPosition.getVelocities()[1] < 5 && newPosition.getVelocities()[1] > -5)) {
                //System.out.println("I am in the if AdvancedBotment and before the assignment.");
                if(newPosition.getVelocities()[0] < 5 || newPosition.getVelocities()[0] > -5 || newPosition.getVelocities()[1] < 5 || newPosition.getVelocities()[1] > -5)
                    currentPosition = newPosition.getAdvancedBot();

            }
            limitation++;

        }
        System.out.println(newPosition.getVelocities());
        System.out.println("done");
        System.out.println(count);

        xbounds[0] = newPosition.getVelocities()[0] - 0.05;
        xbounds[1] = newPosition.getVelocities()[0] + 0.05;
        ybounds[0] = newPosition.getVelocities()[1] - 0.05;
        ybounds[1] = newPosition.getVelocities()[1] + 0.05;
        limitation = 0;

        while (distanceOfVectors(currentPosition, goalPosition) > r.r && limitation != 500) //r.r radius of our hole
        {
            //System.out.println("I am in the while loop - before the if AdvancedBotment.");
            newPosition = findNextAdvancedBot(startPosition, goalPosition, xbounds, ybounds);
            count++;

            if (distanceOfVectors(currentPosition, goalPosition) > distanceOfVectors(newPosition.getAdvancedBot(), goalPosition)) {
                //System.out.println("I am in the if AdvancedBotment and before the assignment.");
                double[] tempvelo = newPosition.getVelocities();
                xbounds[0] = tempvelo[0] - 0.1;
                xbounds[1] = tempvelo[0] + 0.1;
                ybounds[0] = tempvelo[1] - 0.1;
                ybounds[1] = tempvelo[1] + 0.1;
                currentPosition = newPosition.getAdvancedBot();
            }
            limitation++;

        }

        return newPosition;
    }

    public double distanceOfVectors(double[] first, double[] second) {
        return (Math.sqrt((Math.pow(first[0] - second[0], 2)) + (Math.pow(first[1] - second[1], 2))));
    }
}
