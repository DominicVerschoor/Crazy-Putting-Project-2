package com.badlogic.PhyiscSolvers;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.TerrainInput;

import javax.swing.*;

public class Euler implements Solver {

    FileReader read = new FileReader();
    PartialDerivative Derive = new PartialDerivative();
    TerrainInput function = new TerrainInput();
    Acceleration acceleration;

    private final double g = 9.81;
    private double uk = read.muk;
    private double us = read.mus;
    private double h = 0.00001;
    static private double[] tempBallVector = new double[4];

    private float treeX = read.treeX;
    private float treeY = read.treeY;
    private float treeRadius = 2f;

    /**
     * @param ballVector an array that contains x and y positions of the ball and x and y input velocities
     * @return a ballVector representing the resting point of the ball
     */
    @Override
    public double[] solve(double[] ballVector) {

        double initialX = ballVector[0];
        double initialY = ballVector[1];
        ballVector = speedLimit(ballVector);

        while (Math.abs(ballVector[2]) > 0.00001 || Math.abs(ballVector[3]) > 0.00001) {
            uk = read.muk;
            us = read.mus;
            if ((ballVector[0] >= read.sandPitXMin && ballVector[0] <= read.sandPitXMin)
                    && (ballVector[1] >= read.sandPitXMin && ballVector[1] <= read.sandPitXMin)) {
                uk = read.muks;
                us = read.muss;
            }

            tempBallVector[0] = ballVector[0] + h * ballVector[2];
            tempBallVector[1] = ballVector[1] + h * ballVector[3];

            double partialX = Derive.partialX(tempBallVector[0], tempBallVector[1]);
            double partialY = Derive.partialY(tempBallVector[0], tempBallVector[1]);

            double accX = acceleration.accelerationEquationX(ballVector[2], ballVector[3], partialX, partialY);
            double accY = acceleration.accelerationEquationY(ballVector[2], ballVector[3], partialX, partialY);

            tempBallVector[2] = ballVector[2] + h * accX;
            tempBallVector[3] = ballVector[3] + h * accY;
            if (isUnderWater(ballVector)) {
                System.out.println("HELP ME im unda tha wata ");
                return resetLocation(ballVector, initialX, initialY);
            }
            if (isOutOfBounds(ballVector)) {
                System.out.println("BALL OUT OF BOUNDS");
                return resetLocation(ballVector, initialX, initialY);
            }
            if (isHittingTree(ballVector[0], (double)treeX, ballVector[1], (double)treeY, (double)treeRadius)) {
                reverseVelocity(ballVector);
                System.out.println("Boing");
            }

            System.arraycopy(tempBallVector, 0, ballVector, 0, ballVector.length);

            if (isRollingDown(ballVector, partialX, partialY)) {
                rollDown(ballVector, partialX, partialY);
            }
        }
        System.out.println(ballVector[0]);
        System.out.println(ballVector[1]);
        return tempBallVector;
    }

    @Override
    public double[] speedLimit(double[] ballVector) {
        if (ballVector[2] > 5) {
            ballVector[2] = 5;
        }
        if (ballVector[2] < -5) {
            ballVector[2] = -5;
        }
        if (ballVector[3] > 5) {
            ballVector[3] = 5;
        }
        if (ballVector[3] < -5) {
            ballVector[3] = -5;
        }
        return ballVector;
    }

    @Override
    public void accelerationType(boolean buttonInput){
        if (buttonInput){
            acceleration = new BasicAcceleration();
        }else{
            acceleration = new SteepAcceleration();
        }
    }

    @Override
    public boolean isUnderWater(double[] ballVector) {
        return function.terrain(ballVector[0], ballVector[1]) < 0;
    }

    @Override
    public boolean isOutOfBounds(double[] ballVector) {
        return Math.abs(ballVector[0]) > 20 || Math.abs(ballVector[1]) > 20;
    }

    @Override
    public boolean isHittingTree(double x1, double x2, double y1, double y2, double radius) {
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2)) <= radius;
    }

    @Override
    public boolean isRollingDown(double[] ballVector, double partialX, double partialY) {
        return (Math.abs(ballVector[2]) <= 0.00001 && Math.abs(ballVector[3]) <= 0.00001)
                && (Math.abs(partialX) > 0.00001 || Math.abs(partialY) > 0.00001);
    }

    @Override
    public double[] resetLocation(double[] ballVector, double initialX, double initialY) {
        ballVector[0] = initialX;
        ballVector[1] = initialY;
        return ballVector;
    }

    @Override
    public void rollDown(double[] ballVector, double partialx, double partialy) {
        double sqrt = Math.sqrt(partialx * partialx + partialy * partialy);
        if (!(us > sqrt)) {
            ballVector[2] = h * -g * partialx + uk * g * partialx / sqrt;
            ballVector[3] = h * -g * partialy + uk * g * partialy / sqrt;
        }
    }

    @Override
    public void reverseVelocity(double[] ballVector) {
        ballVector[2] = -ballVector[2];
        ballVector[3] = -ballVector[3];
    }
}
