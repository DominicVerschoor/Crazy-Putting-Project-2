package com.badlogic.Screens;

import java.util.Random;

public class AdvancedBot {
    // current position of the ball in x,y direction
    private double[] currentPositions;
    private static FileReader r = new FileReader();

    // our heuristic function that will determine where the ball's next position will be
    private double[] currentVelocities;

    public AdvancedBot(double[] currentPositions, double[] currentVelocities) {
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
    public AdvancedBot findNextAdvancedBot(double[] startPosition, double[] goalPosition, double[] xbounds, double[] ybounds) {
        AdvancedBot newPosition = new AdvancedBot(startPosition, goalPosition);
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
        /*
        newArrXt[0] = 0;
        newArrXt[1] = 0;
        newArrXt[2] = 2;
        newArrXt[3] = 0;
        */

        RK4 rk4 = new RK4();
        double[] fillarray = rk4.newRK4(newArrXt);
        double[] newVels = {fillarray[2], fillarray[3]};
        double[] newPos = {fillarray[0], fillarray[1]};
        newPosition.setPositions(newPos);
        //newPosition.setVelocities(newVels);


        return newPosition;
    }

    // comparing currentPosition to newly calculated Positn of previous method and setting new AdvancedBot
    public AdvancedBot HillClimbingMethod(double[] startPosition, double[] goalPosition)
    //public double HillClimbingMethod(double [] startPosition, double [] goalPosition, double val)
    {
        double[] xbounds = {-5, 5};
        double[] ybounds = {-5, 5};
        double[] currentPosition = startPosition;
        AdvancedBot newPosition = new AdvancedBot(currentPosition, goalPosition);
        AdvancedBot bestPosition = new AdvancedBot(currentPosition, goalPosition);
        int count = 0;
        int limitation = 0;

        // add AdvancedBotment for radius of hole -> so that goalPosition has a variety of option values e.g. 0.1m around the hole
        // distanceOfVectors(currentPosition, goalPosition) <= 0.1

        //while (!Arrays.equals(currentPosition, goalPosition))

        //----------------- Important Change to make here ---------------------

        while (distanceOfVectors(currentPosition, goalPosition) > 1) // Change 0.5 with the radius of our hole!!!!!!!

        //----------------- Important Change to make here ---------------------

        {
            System.out.println("I am in the while loop - before the if AdvancedBotment.");
            newPosition = findNextAdvancedBot(startPosition, goalPosition, xbounds, ybounds);
            count++;

            if (distanceOfVectors(currentPosition, goalPosition) >= distanceOfVectors(newPosition.getAdvancedBot(), goalPosition)) {
                System.out.println("I am in the if AdvancedBotment and before the assignment.");
                double[] tempvelo = newPosition.getVelocities();
                bestPosition = newPosition;
                currentPosition = newPosition.getAdvancedBot();

            }

        }
        System.out.println(newPosition.getVelocities());
        System.out.println("done");
        System.out.println(count);

        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        xbounds[0] = newPosition.getVelocities()[0] - 0.05;
        xbounds[1] = newPosition.getVelocities()[0] + 0.05;
        ybounds[0] = newPosition.getVelocities()[1] - 0.05;
        ybounds[1] = newPosition.getVelocities()[1] + 0.05;


        while (distanceOfVectors(currentPosition, goalPosition) > r.r) // Change 0.5 with the radius of our hole!!!!!!!
        {
            System.out.println("I am in the while loop - before the if AdvancedBotment.");
            newPosition = findNextAdvancedBot(startPosition, goalPosition, xbounds, ybounds);
            count++;

            if (distanceOfVectors(currentPosition, goalPosition) > distanceOfVectors(newPosition.getAdvancedBot(), goalPosition)) {
                System.out.println("I am in the if AdvancedBotment and before the assignment.");
                double[] tempvelo = newPosition.getVelocities();
                xbounds[0] = tempvelo[0] - 0.1;
                xbounds[1] = tempvelo[0] + 0.1;
                ybounds[0] = tempvelo[1] - 0.1;
                ybounds[1] = tempvelo[1] + 0.1;
                bestPosition = newPosition;
                currentPosition = newPosition.getAdvancedBot();

            }

        }

        return newPosition;
    }

    public double distanceOfVectors(double[] first, double[] second) {
        return (Math.sqrt((Math.pow(first[0] - second[0], 2)) + (Math.pow(first[1] - second[1], 2))));
    }
}
