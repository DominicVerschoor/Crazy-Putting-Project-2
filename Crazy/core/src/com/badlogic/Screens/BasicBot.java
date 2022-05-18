package com.badlogic.Screens;

public class BasicBot {

    private static FileReader r = new FileReader();
    private static final RK4 rk4 = new RK4();
    private static final RK2 rk2 = new RK2();
    private static final Euler euler = new Euler();


    public static double[] basicShooting() {
        double holeX = r.xt;
        double holeY = r.yt;
        System.out.println("XGOAL: " + holeX);
        System.out.println("YGOAL: " + holeY);

        double[] arrXt = new double[4];
        double[] save = new double[4];
        double xvel = 0;
        double yvel = 0;
        double xpos = r.x0;
        double ypos = r.y0;

        arrXt[0] = xpos;
        arrXt[1] = ypos;
        arrXt[2] = xvel;
        arrXt[3] = yvel;

        save[0] = xpos;
        save[1] = ypos;
        save[2] = xvel;
        save[3] = yvel;

        while (((save[0] >= holeX + r.r) || (save[0] <= holeY - r.r)) ||
                ((save[1] >= holeX + r.r) || (save[1] <= holeY - r.r))) {
            xvel = -5;
            yvel = -5;
            while ((((save[0] >= holeX + r.r) || (save[0] <= holeY - r.r)) ||
                    ((save[1] >= holeX + r.r) || (save[1] <= holeY - r.r)))
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

                arrXt = euler.Euler(arrXt);

                if (distance(save[0], save[1], holeX, holeY) > distance(arrXt[0], arrXt[1], holeX, holeY)) {
                    save[0] = arrXt[0];
                    save[1] = arrXt[1];
                    save[2] = arrXt[2];
                    save[3] = arrXt[3];
                }
            }

            xpos = save[0];
            ypos = save[1];
            xvel = save[2];
            yvel = save[3];
            System.out.println("EndX: " + xpos + " EndY: " + ypos);
            System.out.println("EndXV: " + xvel + " EndYV: " + yvel);
            System.out.println();
        }
//        }
        System.out.println("Found");

        return arrXt;
    }

    public static double distance(double xpos, double ypos, double holex, double holey) {
        return Math.sqrt(Math.pow((xpos - holex), 2) + Math.pow((ypos - holey), 2));
    }

    public static void main(String[] args) {
        double[] test = basicShooting();
        System.out.println("x: " + test[0]);
        System.out.println("y: " + test[1]);
        System.out.println("xv: " + test[2]);
        System.out.println("yv: " + test[3]);
    }
}
