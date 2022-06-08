package com.badlogic.Screens;

import java.util.Random;

public class HillClimbing {
    int MAX_ITER = 2000;

    RK2New rk2 = new RK2New();
    Random random = new Random();
    FileReader read = new FileReader();
    Vectors currentVec;

    public class Vectors {
        double velx;
        double vely;
        double distance;

        public Vectors(double velx, double vely) {
            this.velx = velx;
            this.vely = vely;
        }
    }

    public HillClimbing() {

    }

    public double[] hillClibing(double x, double y) {
        double vx = (read.xt - x) / 3;
        double vy = (read.yt - y) / 3;
        currentVec = new Vectors(vx, vy);
        double arrxt[] = {x, y, currentVec.velx, currentVec.vely};
        double result[] = rk2.newRK2(arrxt);
        currentVec.distance = distance(result);
        double scale = 0.02;
        int currentItr = 0;
        while (currentItr < MAX_ITER) {

            Vectors[] tmp = new Vectors[4];

            for (int i = 0; i < 4; i++) {

                switch (i) {
                    case 0:
                        tmp[0] = new Vectors(currentVec.velx, currentVec.vely + scale);
                        double[] tmpsolve = {x, y, tmp[0].velx, tmp[0].vely};
                        result = rk2.newRK2(tmpsolve);
                        tmp[0].distance = distance(result);
                        break;
                    case 1:
                        tmp[1] = new Vectors(currentVec.velx, currentVec.vely - scale);
                        double[] tmpsolve1 = {x, y, tmp[1].velx, tmp[1].vely};
                        result = rk2.newRK2(tmpsolve1);
                        tmp[1].distance = distance(result);
                        break;
                    case 2:
                        tmp[2] = new Vectors(currentVec.velx + scale, currentVec.vely);
                        double[] tmpsolve2 = {x, y, tmp[2].velx, tmp[2].vely};
                        result = rk2.newRK2(tmpsolve2);
                        tmp[2].distance = distance(result);
                        break;
                    case 3:
                        tmp[3] = new Vectors(currentVec.velx - scale, currentVec.vely);
                        double[] tmpsolve3 = {x, y, tmp[3].velx, tmp[3].vely};
                        result = rk2.newRK2(tmpsolve3);
                        tmp[3].distance = distance(result);
                        break;

                    default:
                        break;
                }

            }
            currentItr++;
            Vectors newBest = currentVec;
            for (int i = 0; i < tmp.length; i++) {
                if (newBest.distance > tmp[i].distance) {
                    newBest = tmp[i];
                    System.out.println("im for for swap swap me");
                }
            }
            currentVec = newBest;


        }
        double[] newArrXt = new double[4];
        newArrXt[0] = x;
        newArrXt[1] = y;
        newArrXt[2] = currentVec.velx;
        newArrXt[3] = currentVec.vely;
        System.out.println(currentVec.velx);
        System.out.println(currentVec.vely);
        System.out.println(currentItr);
        return newArrXt;
    }

    public double distance(double[] values) {
        return Math.sqrt(Math.pow(values[0] - read.xt, 2) + Math.pow(values[1] - read.yt, 2));
    }
}
