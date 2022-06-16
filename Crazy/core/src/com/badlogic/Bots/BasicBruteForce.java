package com.badlogic.Bots;

import com.badlogic.FileHandling.FileReader;
import com.badlogic.PhyiscSolvers.Rk4;

public class BasicBruteForce {

    private static FileReader r = new FileReader();
    private static final Rk4 rk4 = new Rk4();

    public double[] basicShooting(double xpos, double ypos) {
        double holeX = r.xt;
        double holeY = r.yt;

        double[] arrXt = new double[4];
        double[] save = new double[4];
        double xvel = -5;
        double yvel = -5;

        arrXt[0] = xpos;
        arrXt[1] = ypos;
        arrXt[2] = xvel;
        arrXt[3] = yvel;

        save[0] = xpos;
        save[1] = ypos;
        save[2] = xvel;
        save[3] = yvel;

        while (((r.r <= distance(save[0], save[1], holeX, holeY) ))
                && (yvel <= 5 || (xvel <= 5))) {

            if (xvel <= 5) {
                xvel += Math.max(Math.abs(xpos - holeX) / 10, 0.1);
            }
            if (yvel <= 5) {
                yvel += Math.max(Math.abs(ypos - holeY) / 10, 0.1);
            }

            arrXt[0] = xpos;
            arrXt[1] = ypos;
            arrXt[2] = xvel;
            arrXt[3] = yvel;

            arrXt = rk4.solve(arrXt);

            if (distance(save[0], save[1], holeX, holeY) > distance(arrXt[0], arrXt[1], holeX, holeY)) {
                System.arraycopy(arrXt, 0, save, 0, arrXt.length);
            }
        }

        yvel = -5;
        while (((r.r <= distance(save[0], save[1], holeX, holeY) ))
                && (yvel <= 5)) {

            yvel += Math.abs(ypos - holeY) / 10;

            arrXt[0] = xpos;
            arrXt[1] = ypos;
            arrXt[2] = 0;
            arrXt[3] = yvel;

            arrXt = rk4.solve(arrXt);

            if (distance(save[0], save[1], holeX, holeY) > distance(arrXt[0], arrXt[1], holeX, holeY)) {
                System.arraycopy(arrXt, 0, save, 0, arrXt.length);
            }
        }

        xvel = -5;
        while (((r.r <= distance(save[0], save[1], holeX, holeY) ))
                && (xvel <= 5)) {

            xvel += Math.abs(xpos - holeY) / 10;

            arrXt[0] = xpos;
            arrXt[1] = ypos;
            arrXt[2] = xvel;
            arrXt[3] = 0;

            arrXt = rk4.solve(arrXt);

            if (distance(save[0], save[1], holeX, holeY) > distance(arrXt[0], arrXt[1], holeX, holeY)) {
                System.arraycopy(arrXt, 0, save, 0, arrXt.length);
            }
        }

        xpos = save[0];
        ypos = save[1];
        xvel = save[2];
        yvel = save[3];
        System.out.println("EndX: " + xpos + " EndY: " + ypos);
        System.out.println("EndXV: " + xvel + " EndYV: " + yvel);
        System.out.println();
        return save;
    }

    public static double distance(double xpos, double ypos, double holex, double holey) {
        return Math.sqrt(Math.pow((xpos - holex), 2) + Math.pow((ypos - holey), 2));
    }
}
