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
    private boolean drowned = false;
    private boolean outOfBounds = false;

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
            if (function.terrain(tempBallVector[0], tempBallVector[1]) < 0) {
                tempBallVector[0] = initialX;
                tempBallVector[1] = initialY;
                System.arraycopy(tempBallVector, 0, ballVector, 0, ballVector.length);

                JOptionPane.showMessageDialog(new JFrame(), "Ball under water");
                return tempBallVector;
            }
            if (tempBallVector[0] > 20 || tempBallVector[0] < -20 || tempBallVector[1] > 20 || tempBallVector[1] < -20) {
                tempBallVector[0] = initialX;
                tempBallVector[1] = initialY;
                System.arraycopy(tempBallVector, 0, ballVector, 0, ballVector.length);
                JOptionPane.showMessageDialog(new JFrame(), "Ball out of bounds");
                return tempBallVector;
            }

            System.arraycopy(tempBallVector, 0, ballVector, 0, ballVector.length);

            if ((Math.abs(ballVector[2]) <= 0.00001 && Math.abs(ballVector[3]) <= 0.00001) && (Math.abs(partialX) > 0.00001 || Math.abs(partialY) > 0.00001)) {
                double sqrt = Math.sqrt(partialX * partialX + partialY * partialY);
                if (us > sqrt) {
                    break;
                } else {
                    ballVector[2] = h * -g * partialX + uk * g * partialX / sqrt;
                    ballVector[3] = h * -g * partialY + uk * g * partialY / sqrt;

                }
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


    public boolean getDrowned() {
        return drowned;
    }

    public boolean getOutOfBounds() {
        return outOfBounds;
    }
}
