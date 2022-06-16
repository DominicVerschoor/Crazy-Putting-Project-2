package com.badlogic.Bots;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.PhyiscSolvers.Euler;

public class RuleBasedBot {

    private final FileReader r = new FileReader();
    private final Euler euler = new Euler();
    private final double reductionRate = 0.95;
    private final double holeX = r.xt;
    private final double holeY = r.yt;
    private final double pi = 3.14159265359;

    public double[] shoot(double xpos, double ypos) {
        euler.accelerationType(true);

        double[] arrXt = new double[4];
        double xVel = holeX - xpos;
        double yVel = holeY - ypos;

        while (Math.abs(xVel) > 5 || Math.abs(yVel) > 5 ){
            xVel = xVel * reductionRate;
            yVel = yVel * reductionRate;
        }

        arrXt[0] = xpos;
        arrXt[1] = ypos;
        arrXt[2] = xVel;
        arrXt[3] = yVel;

        if (isDrowned(arrXt)){
            System.out.println("Drown");
            waterScenario(arrXt);
        }

        if (overshoot(arrXt)){
            System.out.println("Overshoot");
            overshootScenario(arrXt);
        }

        while (isOutOfBounds(arrXt)) {
            System.out.println("OOB");
            arrXt[2] = xVel * reductionRate;
            arrXt[3] = yVel * reductionRate;
            xVel = arrXt[2];
            yVel = arrXt[3];
        }

        return arrXt;
    }

    private void waterScenario(double[] arrXt){
        double xVel = arrXt[2];
        double yVel = arrXt[3];

        while (isDrowned(arrXt) && notInHole(arrXt)) {
            arrXt[2] = xVel * Math.cos(5*(pi/180) * arrXt[0])
                    - yVel * Math.sin(5*(pi/180) * arrXt[1]);

            arrXt[3] = xVel * Math.sin(5*(pi/180) * arrXt[0])
                    + yVel * Math.cos(5*(pi/180) * arrXt[1]);

            xVel = arrXt[2];
            yVel = arrXt[3];
        }

        while (isOutOfBounds(arrXt)) {
            arrXt[2] = xVel * reductionRate;
            arrXt[3] = yVel * reductionRate;
            xVel = arrXt[2];
            yVel = arrXt[3];
        }

        System.out.println("xv "+xVel);
        System.out.println("yv "+yVel);
    }

    private void overshootScenario(double[] arrXt){
        double xVel = arrXt[2];
        double yVel = arrXt[3];
        double[] testDrowning = new double[4];

        while (overshoot(arrXt) && notInHole(arrXt)){
            System.arraycopy(arrXt, 0, testDrowning, 0, arrXt.length);
            testDrowning[2] = xVel * reductionRate;
            testDrowning[3] = yVel * reductionRate;
            if (!isDrowned(testDrowning)) {
                arrXt[2] = xVel * reductionRate;
                arrXt[3] = yVel * reductionRate;
                xVel = arrXt[2];
                yVel = arrXt[3];
            }
            else {
                break;
            }
        }

        while (isOutOfBounds(arrXt)) {
            arrXt[2] = xVel * reductionRate;
            arrXt[3] = yVel * reductionRate;
            xVel = arrXt[2];
            yVel = arrXt[3];
        }

        System.out.println("xv "+xVel);
        System.out.println("yv "+yVel);
    }

    private boolean overshoot(double[] arrXt){
        double oldXVel = arrXt[2];
        double oldYVel = arrXt[3];
        double[] temp = simulate(arrXt);

        return !((oldXVel >= 0 && holeX - temp[0] >= 0 || oldXVel < 0 && holeX - temp[0] < 0)
                && (oldYVel >= 0 && holeY - temp[1] >= 0 || oldYVel < 0 && holeY - temp[1] < 0))
                && !isDrowned(temp) || isOutOfBounds(arrXt);
    }

    private boolean isOutOfBounds(double[] arrXt) {
        simulate(arrXt);
        return euler.getOutOfBounds();
    }

    private boolean isDrowned(double[] arrXt) {
        simulate(arrXt);
        return euler.getDrowned();
    }

    private boolean notInHole(double[] arrXt){
        double[] temp = simulate(arrXt);
        return !(r.r >= distance(temp[0], temp[1]));
    }

    private double distance(double xpos, double ypos) {
        return Math.sqrt(Math.pow((xpos - holeX), 2) + Math.pow((ypos - holeY), 2));
    }

    private double[] simulate(double[] arrXt){
        double[] temp = new double[4];
        System.arraycopy(arrXt, 0, temp, 0, arrXt.length);
        euler.solve(temp);
        return temp;
    }
}
