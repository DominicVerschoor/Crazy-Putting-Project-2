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

        arrXt = rk4.solve(arrXt);
        System.out.println("xp: " + arrXt[0]);
        System.out.println("yp: " + arrXt[1]);
        return arrXt;
    }

<<<<<<< HEAD
    private void waterScenario(double[] arrXt){
        double xVel = arrXt[2];
        double yVel = arrXt[3];

        while (isDrowned(arrXt) && notInHole(arrXt)) {
            arrXt[2] = xVel * Math.cos(5*(pi/180))
                    - yVel * Math.sin(5*(pi/180));

            arrXt[3] = xVel * Math.sin(5*(pi/180))
                    + yVel * Math.cos(5*(pi/180));

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

=======
>>>>>>> f31c30cbdda80c7f4da942cbd9d38cc5be5a2a9b
    private boolean isOutOfBounds(double[] arrXt) {
        rk4.solve(arrXt);
        return rk4.getOutOfBounds();
    }

    private boolean isDrowned(double[] arrXt) {
        rk4.solve(arrXt);
        return rk4.getDrowned();
    }
}
