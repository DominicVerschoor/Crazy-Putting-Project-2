package com.badlogic.PhyiscSolvers;

import com.badlogic.Bots.AdvancedHillClibing;
import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.TerrainInput;
import com.badlogic.GameScreens.OptionsGameScreen;

public class Rk2 implements Solver {
    PartialDerivative derive = new PartialDerivative();
    FileReader read = new FileReader();
    Acceleration acceleration;
    private final double g = 9.81;
    double h = 0.0001;
    private double uk = read.muk;     // kinetic friction coefficient of grass
    private double us = read.mus;
    TerrainInput function = new TerrainInput();

    /**
     * @param ballVector an array that contains x and y positions of the ball and x and y input velocities
     * @return a ballVector representing the resting point of the ball
     */
    public double[] solve(double[] ballVector) {
        double initialX = ballVector[0];
        double initialY = ballVector[1];
        if (!OptionsGameScreen.hillClimbing){
            ballVector = speedLimit(ballVector);
        }

        while (Math.abs(ballVector[2]) > 0.001 || Math.abs(ballVector[3]) > 0.001) {
            uk = read.muk;
            us = read.mus;
            if ((ballVector[0] >= read.sandPitXMin && ballVector[0] <= read.sandPitXMin)
                    && (ballVector[1] >= read.sandPitXMin && ballVector[1] <= read.sandPitXMin)) {
                uk = read.muks;
                us = read.muss;
                System.out.println("ew sand");
            }


            double[] k1 = k1Calculations(ballVector);
            double[] k2 = k2Calculations(k1);
            double[] finalCalc = finalCalculations(k1, k2);

            ballVector[0] += finalCalc[0];
            ballVector[1] += finalCalc[1];
            ballVector[2] += finalCalc[2];
            ballVector[3] += finalCalc[3];

            double partialx = derive.partialX(ballVector[0], ballVector[1]);
            double partialy = derive.partialX(ballVector[0], ballVector[1]);

            if (function.terrain(ballVector[0], ballVector[1]) < 0) {
                ballVector[0] = initialX;
                ballVector[1] = initialY;
                System.out.println("HELP ME im unda tha wata ");
                return ballVector;
            }
<<<<<<< HEAD
            if (!OptionsGameScreen.hillClimbing && isOutOfBounds(ballVector)) {
=======
            if ((ballVector[0] > 21 || ballVector[0] < -21 || ballVector[1] > 21 || ballVector[1] < -21)&&!AdvancedHillClibing.inAdvancedBot) {
                ballVector[0] = initialX;
                ballVector[1] = initialY;
>>>>>>> f31c30cbdda80c7f4da942cbd9d38cc5be5a2a9b
                System.out.println("BALL OUT OF BOUNDS");
                return ballVector;
            }
        }

        System.out.println("X: " + ballVector[0]);
        System.out.println("Y: " + ballVector[1]);
        return ballVector;
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

    public void accelerationType(boolean buttonInput){
        if (buttonInput){
            acceleration = new BasicAcceleration();
        }else{
            acceleration = new SteepAcceleration();
        }
    }

<<<<<<< HEAD
    @Override
    public boolean isUnderWater(double[] ballVector) {
        return function.terrain(ballVector[0], ballVector[1]) < 0;
    }

    @Override
    public boolean isOutOfBounds(double[] ballVector) {
        return ballVector[0] > 20 || ballVector[0] < -20 || ballVector[1] > 20 || ballVector[1] < -20;
    }

    @Override
    public boolean isHittingTree(double x1, double x2, double y1, double y2, double radius) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) <= radius;
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

    double[] k1Calculations(double[] ballVector) {
=======
    private double[] k1Calculations(double[] ballVector) {
>>>>>>> f31c30cbdda80c7f4da942cbd9d38cc5be5a2a9b
        double[] k1Final = new double[6];
        k1Final[0] = ballVector[0];
        k1Final[1] = ballVector[1];
        k1Final[2] = ballVector[2];
        k1Final[3] = ballVector[3];
        double partialx = derive.partialX(k1Final[0], k1Final[1]);
        double partialy = derive.partialY(k1Final[0], k1Final[1]);
        k1Final[4] = acceleration.accelerationEquationX(k1Final[2], k1Final[3], partialx, partialy);
        k1Final[5] = acceleration.accelerationEquationY(k1Final[2], k1Final[3], partialx, partialy);
        return k1Final;
    }

    private double[] k2Calculations(double[] k1) {
        double[] k2Final = new double[6];
        k2Final[0] = k1[0] + (h * k1[2]) * (2.0 / 3);
        k2Final[1] = k1[1] + (h * k1[3]) * (2.0 / 3);
        k2Final[2] = k1[2] + (h * k1[4]) * (2.0 / 3);
        k2Final[3] = k1[3] + (h * k1[5]) * (2.0 / 3);
        double partialx = derive.partialX(k2Final[0], k2Final[1]);
        double partialy = derive.partialY(k2Final[0], k2Final[1]);
        k2Final[4] = acceleration.accelerationEquationX(k2Final[2], k2Final[3], partialx, partialy);
        k2Final[5] = acceleration.accelerationEquationY(k2Final[2], k2Final[3], partialx,partialy);
        return k2Final;
    }

    private double[] finalCalculations(double[] k1, double[] k2) {
        double[] finalCalc = new double[4];
        finalCalc[0] = (k1[2] * h * (1 / 4.0)) + (3 / 4.0) * (k2[2] * h);
        finalCalc[1] = (k1[3] * h * (1 / 4.0)) + (3 / 4.0) * (k2[3] * h);
        finalCalc[2] = (k1[4] * h * (1 / 4.0)) + (3 / 4.0) * (k2[4] * h);
        finalCalc[3] = (k1[5] * h * (1 / 4.0)) + (3 / 4.0) * (k2[5] * h);

        return finalCalc;
    }
<<<<<<< HEAD

    public boolean getDrowned() {
        return drowned;
    }

    public boolean getOutOfBounds() {
        return outOfBounds;
    }
=======
>>>>>>> f31c30cbdda80c7f4da942cbd9d38cc5be5a2a9b
}
