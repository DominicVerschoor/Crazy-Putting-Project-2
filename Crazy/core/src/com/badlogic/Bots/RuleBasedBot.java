package com.badlogic.Bots;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.PhyiscSolvers.Euler;
import com.badlogic.PhyiscSolvers.Rk4;

import java.util.Arrays;

public class RuleBasedBot {

    private FileReader r = new FileReader();
    private final Euler rk4 = new Euler();
    private final double holeX = r.xt;
    private final double holeY = r.yt;
    private final double pi = 3.14159265359;

    public double[] shoot(double xpos, double ypos) {
        rk4.accelerationType(true);

        double[] arrXt = new double[4];
        double xVel = holeX - xpos;
        double yVel = holeY - ypos;

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
            arrXt[2] = xVel * 0.9;
            arrXt[3] = yVel * 0.9;
            xVel = arrXt[2];
            yVel = arrXt[3];
        }

        System.out.println("Neither");
        rk4.solve(arrXt);
        System.out.println("xp "+arrXt[0]);
        System.out.println("yp "+arrXt[1]);
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
            arrXt[2] = xVel * 0.9;
            arrXt[3] = yVel * 0.9;
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
            testDrowning[2] = xVel * 0.9;
            testDrowning[3] = yVel * 0.9;
            if (!isDrowned(testDrowning)) {
                arrXt[2] = xVel * 0.9;
                arrXt[3] = yVel * 0.9;
                xVel = arrXt[2];
                yVel = arrXt[3];
            }
            else {
                break;
            }
        }

        while (isOutOfBounds(arrXt)) {
            arrXt[2] = xVel * 0.9;
            arrXt[3] = yVel * 0.9;
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
                && !isDrowned(temp);
    }

    private boolean isOutOfBounds(double[] arrXt) {
        simulate(arrXt);
        return rk4.getOutOfBounds();
    }

    private boolean isDrowned(double[] arrXt) {
        simulate(arrXt);
        return rk4.getDrowned();
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
        rk4.solve(temp);
        return temp;
    }
}
