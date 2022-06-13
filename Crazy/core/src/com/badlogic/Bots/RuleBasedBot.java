package com.badlogic.Bots;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.PhyiscSolvers.Rk4;

public class RuleBasedBot {

    private static FileReader r = new FileReader();
    private static final Rk4 rk4 = new Rk4();
    private static final double holeX = r.xt;
    private static final double holeY = r.yt;
    private static final double pi = 3.14159265359;


    public static double[] shoot(double xpos, double ypos) {

        double[] arrXt = new double[4];
        double xVel = holeX - xpos;
        double yVel = holeY - ypos;

        arrXt[0] = xpos;
        arrXt[1] = ypos;
        arrXt[2] = xVel;
        arrXt[3] = yVel;

        if (isDrowned(arrXt)){
            return rk4.solve(waterScenario(arrXt));
        }

        if (overshoot(arrXt)){
            return rk4.solve(overshootScenario(arrXt));
        }

        arrXt = rk4.solve(arrXt);
        return arrXt;
    }

    private static double[] waterScenario(double[] arrXt){
        double xVel = holeX - arrXt[0];
        double yVel = holeY - arrXt[1];

        while (isDrowned(arrXt)) {
            arrXt[2] = xVel * Math.cos(5*(pi/180) * arrXt[0])
                    - yVel * Math.sin(5*(pi/180) * arrXt[1]);

            arrXt[3] = xVel * Math.sin(5*(pi/180) * arrXt[0])
                    + yVel * Math.cos(5*(pi/180) * arrXt[1]);

            xVel = arrXt[2];
            yVel = arrXt[3];
            System.out.println("x "+xVel);
            System.out.println("y "+yVel);
            System.out.println();
        }

//        while (isOutOfBounds(arrXt)) {
//            arrXt[2] = xVel * 0.9;
//            arrXt[3] = yVel * 0.9;
//            xVel = arrXt[2];
//            yVel = arrXt[3];
//        }

        return arrXt;
    }

    private static double[] overshootScenario(double[] arrXt){
        double xVel = holeX - arrXt[0];
        double yVel = holeY - arrXt[1];

        while (overshoot(arrXt)){
            arrXt[2] = xVel * 0.9;
            arrXt[3] = yVel * 0.9;
            xVel = arrXt[2];
            yVel = arrXt[3];
        }

//        while (isOutOfBounds(arrXt)) {
//            arrXt[2] = xVel * 0.9;
//            arrXt[3] = yVel * 0.9;
//            xVel = arrXt[2];
//            yVel = arrXt[3];
//        }

        return arrXt;
    }

    private static boolean overshoot(double[] arrXt){
        double oldXVel = arrXt[2];
        double oldYVel = arrXt[3];

        arrXt = rk4.solve(arrXt);

        if ((oldXVel < 0)? (holeX - arrXt[2] > 0): (holeX - arrXt[2] < 0) &&
                (oldYVel < 0)? (holeY - arrXt[3] > 0): (holeY - arrXt[3] < 0)){
            return true;
        }

        else return (oldXVel == 0 && (oldYVel < 0) ? (holeY - arrXt[3] > 0) : (holeY - arrXt[3] < 0))
                || (oldYVel == 0 && (oldXVel < 0) ? (holeX - arrXt[2] > 0) : (holeX - arrXt[2] < 0));
    }

    private static boolean isOutOfBounds(double[] arrXt) {
        rk4.solve(arrXt);
        return rk4.getOutOfBounds();
    }

    private static boolean isDrowned(double[] arrXt) {
        rk4.solve(arrXt);
        return rk4.getDrowned();
    }

    public static void main(String[] args) {
        double[] arrXt;

        rk4.accelerationType(true);
        arrXt = shoot(1,1);

        System.out.println("xp: " + arrXt[0]);
        System.out.println("yp: " + arrXt[1]);
    }
}
