package com.badlogic.Screens;

public class RuleBasedBot {

    private static FileReader r = new FileReader();
    private static final RK4 rk4 = new RK4();

    public double[] shoot(double xpos, double ypos) {
        double holeX = r.xt;
        double holeY = r.yt;

        double[] arrXt = new double[4];
        double xvel = (holeX - xpos) / 3;
        double yvel = (holeY - ypos) / 3;
        boolean switchARoo = true;

        arrXt[0] = xpos;
        arrXt[1] = ypos;
        arrXt[2] = xvel;
        arrXt[3] = yvel;

        while (isDrowned(arrXt)) {
            if (switchARoo) {
                xvel++;
                yvel++;
            } else {
                xvel--;
                yvel--;
            }
            System.out.println("drowned lol");
            switchARoo = !switchARoo;
        }

        while (isOutOfBounds(arrXt)) {
            xvel = xvel / 2;
            yvel = yvel / 2;
            System.out.println("out of bounds lol");
        }

        arrXt = rk4.newRK4(arrXt);
        System.out.println("xp: " + arrXt[0]);
        System.out.println("yp: " + arrXt[1]);
        return arrXt;
    }

    private boolean isOutOfBounds(double[] arrXt) {
        rk4.newRK4(arrXt);
        return rk4.getOutOfBounds();
    }

    private boolean isDrowned(double[] arrXt) {
        rk4.newRK4(arrXt);
        return rk4.getDrowned();
    }
}
