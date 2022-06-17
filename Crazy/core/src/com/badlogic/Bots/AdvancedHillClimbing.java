package com.badlogic.Bots;


import com.badlogic.FileHandling.FileReader;
import com.badlogic.PhyiscSolvers.Rk2;


import java.util.Random;

public class AdvancedHillClimbing {
    int MAX_ITER = 2000;

    Rk2 rk2 = new Rk2();
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


    public double[] hillClimbing(double x, double y) {
        double vx = random.nextDouble() * 5;
        double vy = Math.sqrt(Math.pow(5, 2) - Math.pow(vx, 2));
        currentVec = new Vectors(vx, vy);
        rk2.accelerationType(true);
        double arrxt[] = {x, y, currentVec.velx, currentVec.vely};
        double result[] = rk2.solve(arrxt);
        currentVec.distance = distance(result);
        double scale = 0.1;
        int currentItr = 0;
        while (currentItr < MAX_ITER) {

            Vectors[] tmp = new Vectors[8];

            for (int i = 0; i < 8; i++) {

                switch (i) {
                    case 0:
                        tmp[0] = new Vectors(currentVec.velx, currentVec.vely + scale);
                        simulate(tmp[0], x, y);
                        break;
                    case 1:
                        tmp[1] = new Vectors(currentVec.velx, currentVec.vely - scale);
                        simulate(tmp[1], x, y);
                        break;
                    case 2:
                        tmp[2] = new Vectors(currentVec.velx + scale, currentVec.vely);
                        simulate(tmp[2], x, y);
                        break;
                    case 3:
                        tmp[3] = new Vectors(currentVec.velx - scale, currentVec.vely);
                        simulate(tmp[3], x, y);
                        break;
                    case 4:
                        tmp[4] = new Vectors(currentVec.velx - scale, currentVec.vely - scale);
                        simulate(tmp[4], x, y);
                        break;
                    case 5:
                        tmp[5] = new Vectors(currentVec.velx + scale, currentVec.vely + scale);
                        simulate(tmp[5], x, y);
                        break;
                    case 6:
                        tmp[6] = new Vectors(currentVec.velx - scale, currentVec.vely + scale);
                        simulate(tmp[6], x, y);
                        break;
                    case 7:
                        tmp[7] = new Vectors(currentVec.velx + scale, currentVec.vely - scale);
                        simulate(tmp[7], x, y);
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
                    System.out.println("Im definetlee calculating correctly");
                }
            }
            currentVec = newBest;
        }
        double[] newArrXt = new double[4];
        newArrXt[0] = x;
        newArrXt[1] = y;
        newArrXt[2] = currentVec.velx;
        newArrXt[3] = currentVec.vely;
//        System.out.println(currentVec.velx);
//        System.out.println(currentVec.vely);
//
//        System.out.println(currentItr);
        System.out.println("xv: "+vx);
        System.out.println("yv: "+vy);
        return newArrXt;
    }

    public void simulate(Vectors vector, double x, double y){
        double[] tmpsolve = {x, y, vector.velx, vector.vely};
        double[] result = rk2.solve(tmpsolve);
        vector.distance = distance(result);
    }

    public double distance(double[] values) {
        return Math.sqrt(Math.pow(values[0] - read.xt, 2) + Math.pow(values[1] - read.yt, 2));
    }
}
