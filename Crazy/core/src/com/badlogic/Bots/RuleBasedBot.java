package com.badlogic.Bots;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.PhyiscSolvers.Rk4;

public class RuleBasedBot {

    private static FileReader r = new FileReader();
    private static final Rk4 rk4 = new Rk4();

    public double[] shoot(double xpos, double ypos) {
        double holeX = r.xt;
        double holeY = r.yt;

        double[] arrXt = new double[4];
        double xvel = holeX - xpos;
        double yvel = holeY - ypos;

        arrXt[0] = xpos;
        arrXt[1] = ypos;
        arrXt[2] = xvel;
        arrXt[3] = yvel;

        while (isDrowned(arrXt)) {

        }

        while (isOutOfBounds(arrXt)) {

        }

        arrXt = rk4.solve(arrXt);
        System.out.println("xp: " + arrXt[0]);
        System.out.println("yp: " + arrXt[1]);
        return arrXt;
    }

    private boolean isOutOfBounds(double[] arrXt) {
        rk4.solve(arrXt);
        return rk4.getOutOfBounds();
    }

    private boolean isDrowned(double[] arrXt) {
        rk4.solve(arrXt);
        return rk4.getDrowned();
    }
}
