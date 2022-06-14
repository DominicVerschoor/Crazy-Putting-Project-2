package com.badlogic.PhyiscSolvers;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.GameLogistics.TerrainInput;


public class Rk4 implements Solver {
    PartialDerivative derive = new PartialDerivative();
    FileReader read = new FileReader();
    TerrainInput function = new TerrainInput();
    Acceleration acceleration;
    private final double g = 9.81;
    double h = 0.00001;
    private double uk = read.muk;     // kinetic friction coefficient of grass
    private double us = read.mus;
    private boolean drowned = false;
    private boolean outOfBounds = false;

    /**
     * @param ballVector an array that contains x and y positions of the ball and x and y input velocities
     * @return a ballVector representing the resting point of the ball
     */
    public double[] solve(double[] ballVector) {
        double initialX=ballVector[0];
        double initialY=ballVector[1];
        ballVector = speedLimit(ballVector);

        while (Math.abs(ballVector[2]) > 0.00001 || Math.abs(ballVector[3]) > 0.00001) {
            drowned = false;
            outOfBounds = false;
            uk = read.muk;
            us = read.mus;
            if ((ballVector[0] >= read.sandPitXMin && ballVector[0] <= read.sandPitXMin)
                    && (ballVector[1] >= read.sandPitXMin && ballVector[1] <= read.sandPitXMin)){
                uk = read.muks;
                us = read.muss;
                //System.out.println("ew sand");
            }

            double partialx = derive.partialX(ballVector[0], ballVector[1]);
            double partialy = derive.partialY(ballVector[0], ballVector[1]);

            double tempBallVector[] = new double[4];
            System.arraycopy(ballVector, 0, tempBallVector, 0, ballVector.length);

            double k1posX = tempBallVector[0];
            double k1posY = tempBallVector[1];
            double k1VelX = tempBallVector[2];
            double k1VelY = tempBallVector[3];
            partialx = derive.partialX(k1posX, k1posY);
            partialy = derive.partialY(k1posX, k1posY);
            double k1AccelerationX = acceleration.accelerationEquationX(k1VelX, k1VelY, partialx, partialy);
            double k1AccelerationY = acceleration.accelerationEquationY(k1VelX, k1VelY, partialx, partialy);

            double k2posX = k1posX + (h * k1VelX) * (1.0 / 2);
            double k2posY = k1posY + (h * k1VelY) * (1.0 / 2);
            double k2VelX = k1VelX + (h * k1AccelerationX) * (1.0 / 2);
            double k2VelY = k1VelY + (h * k1AccelerationY) * (1.0 / 2);
            partialx = derive.partialX(k2posX, k2posY);
            partialy = derive.partialY(k2posX, k2posY);


            double k2AccelerationX = acceleration.accelerationEquationX(k2VelX, k2VelY, partialx, partialy);
            double k2AccelerationY = acceleration.accelerationEquationY(k2VelX, k2VelY, partialx, partialy);

            double k3posX = k1posX + (h * k2VelX) * (1.0 / 2);
            double k3posY = k1posY + (h * k2VelY) * (1.0 / 2);
            double k3VelX = k1VelX + (h * k2AccelerationX) * (1.0 / 2);
            double k3VelY = k1VelY + (h * k2AccelerationY) * (1.0 / 2);
            partialx = derive.partialX(k3posX, k3posY);
            partialy = derive.partialY(k3posX, k3posY);

            double k3AccelerationX = acceleration.accelerationEquationX(k3VelX, k3VelY, partialx, partialy);
            double k3AccelerationY = acceleration.accelerationEquationY(k3VelX, k3VelY, partialx, partialy);

            double k4posX = k1posX + (h * k3VelX);
            double k4posY = k1posY + (h * k3VelY);
            double k4VelX = k1VelX + (h * k3AccelerationX);
            double k4VelY = k1VelY + (h * k3AccelerationY);
            partialx = derive.partialX(k4posX, k4posY);
            partialy = derive.partialY(k4posX, k4posY);

            double k4AccelerationX = acceleration.accelerationEquationX(k4VelX, k4VelY, partialx, partialy);
            double k4AccelerationY = acceleration.accelerationEquationY(k4VelX, k4VelY, partialx, partialy);

            double calculatedXPos = (1.0 / 6) * (k1VelX + 2 * k2VelX + 2 * k3VelX + k4VelX) * h;
            double calculatedYPos = (1.0 / 6) * (k1VelY + 2 * k2VelY + 2 * k3VelY + k4VelY) * h;
            double calculatedXVel = (1.0 / 6) * (k1AccelerationX + 2 * k2AccelerationX + 2 * k3AccelerationX + k4AccelerationX) * h;
            double calculatedYVel = (1.0 / 6) * (k1AccelerationY + 2 * k2AccelerationY + 2 * k3AccelerationY + k4AccelerationY) * h;

            ballVector[0] += calculatedXPos;
            ballVector[1] += calculatedYPos;
            ballVector[2] += calculatedXVel;
            ballVector[3] += calculatedYVel;

            partialx = derive.partialX(ballVector[0], ballVector[1]);
            partialy = derive.partialY(ballVector[0], ballVector[1]);

            if (function.terrain(ballVector[0], ballVector[1]) < 0) {
                ballVector[0] = initialX;
                ballVector[1] = initialY;
                System.arraycopy(ballVector, 0, ballVector, 0, ballVector.length);
                drowned = true;
                //System.out.println("HELP ME im unda tha wata ");
                return ballVector;
            }
            if (ballVector[0]>20 || ballVector[0]<-20 || ballVector[1]>20 || ballVector[1]<-20) {
                ballVector[0] = initialX;
                ballVector[1] = initialY;
                System.arraycopy(ballVector, 0, ballVector, 0, ballVector.length);
                outOfBounds = true;
                //System.out.println("BALL OUT OF BOUNDS");
                return ballVector;
            }

            if ((Math.abs(ballVector[2]) <= 0.00001 && Math.abs(ballVector[3]) <= 0.00001) && (Math.abs(partialx) > 0.00001 || Math.abs(partialy) > 0.00001)) {
                double sqrt = Math.sqrt(partialx * partialx + partialy * partialy);
                if (us > sqrt) {
                    break;
                } else {
                    ballVector[2] = h * -g * partialx + uk * g * partialx / sqrt;
                    ballVector[3] = h * -g * partialy + uk * g * partialy / sqrt;

                }
            }

        }

//        System.out.println("X: " + ballVector[0]);
//        System.out.println("Y: " + ballVector[1]);
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

    private double[] k1Calculations(double[] ballVector) {
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
        k2Final[0] = k1[0] + (h * k1[2]) * (1.0 / 2);
        k2Final[1] = k1[1] + (h * k1[3]) * (1.0 / 2);
        k2Final[2] = k1[2] + (h * k1[4]) * (1.0 / 2);
        k2Final[3] = k1[3] + (h * k1[5]) * (1.0 / 2);
        double partialx = derive.partialX(k2Final[0], k2Final[1]);
        double partialy = derive.partialY(k2Final[0], k2Final[1]);
        k2Final[4] = acceleration.accelerationEquationX(k2Final[2], k2Final[3], partialx, partialy);
        k2Final[5] = acceleration.accelerationEquationY(k2Final[2], k2Final[3], partialx, partialy);
        return k2Final;
    }

    private double[] k3Calculations(double[] k1, double[] k2) {
        double[] k3Final = new double[6];
        k3Final[0] = k1[0] + (h * k2[2]) * (1.0 / 2);
        k3Final[1] = k1[1] + (h * k2[3]) * (1.0 / 2);
        k3Final[2] = k1[2] + (h * k2[4]) * (1.0 / 2);
        k3Final[3] = k1[3] + (h * k2[5]) * (1.0 / 2);
        double partialx = derive.partialX(k3Final[0], k3Final[1]);
        double partialy = derive.partialY(k3Final[0], k3Final[1]);
        k3Final[4] = acceleration.accelerationEquationX(k3Final[2], k3Final[3], partialx, partialy);
        k3Final[5] = acceleration.accelerationEquationY(k3Final[2], k3Final[3], partialx, partialy);
        return k3Final;
    }

    private double[] k4Calculations(double[] k1, double[] k3) {
        double[] k4Final = new double[6];
        k4Final[0] = k1[0] + (h * k3[2]) * (1.0 / 2);
        k4Final[1] = k1[1] + (h * k3[3]) * (1.0 / 2);
        k4Final[2] = k1[2] + (h * k3[4]) * (1.0 / 2);
        k4Final[3] = k1[3] + (h * k3[5]) * (1.0 / 2);
        double partialx = derive.partialX(k4Final[0], k4Final[1]);
        double partialy = derive.partialY(k4Final[0], k4Final[1]);
        k4Final[4] = acceleration.accelerationEquationX(k4Final[2], k4Final[3], partialx, partialy);
        k4Final[5] = acceleration.accelerationEquationY(k4Final[2], k4Final[3], partialx, partialy);
        return k4Final;
    }

    private double[] finalCalculations(double[] k1, double[] k2, double[] k3, double[] k4) {
        double[] finalCalc = new double[4];
        finalCalc[0] = (1.0 / 6) * (k1[2] + 2 * k2[2] + 2 * k3[2] + k4[2]) * h;
        finalCalc[1] = (1.0 / 6) * (k1[3] + 2 * k2[3] + 2 * k3[3] + k4[3]) * h;
        finalCalc[2] = (1.0 / 6) * (k1[4] + 2 * k2[4] + 2 * k3[4] + k4[4]) * h;
        finalCalc[3] = (1.0 / 6) * (k1[5] + 2 * k2[5] + 2 * k3[5] + k4[5]) * h;

        return finalCalc;
    }

    public boolean getDrowned() {
        return drowned;
    }

    public boolean getOutOfBounds() {
        return outOfBounds;
    }
}
