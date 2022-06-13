package com.badlogic.Bots;


import com.badlogic.FileHandling.FileReader;
import com.badlogic.PhyiscSolvers.Rk2;


import java.util.Random;

public class AdvancedHillClibing{
    int MAX_ITER = 5000;

    private Rk2 rk2 = new Rk2();
    private Random random = new Random();
    private FileReader read = new FileReader();
    private Vectors currentVec;
    public static boolean inAdvancedBot = true;
    public class Vectors{
        double velx;
        double vely;
        double distance;
        public Vectors(double velx,double vely){
            this.velx=velx;
            this.vely=vely;
        }
    }


    public double[]hillClibing(double x, double y){
        double vx = (read.xt - x) / 3;
        double vy = (read.yt - y) / 3;
        currentVec = new Vectors(vx,vy);
        double arrxt[] = {x,y,currentVec.velx,currentVec.vely};
        double result[] = rk2.solve(arrxt);
        currentVec.distance= distance(result);
        double scale = 0.1;
        int currentItr = 0;
        while (currentItr < MAX_ITER) {

            Vectors[] tmp = new Vectors[7];

            for (int i = 0; i < 7; i++) {

                switch (i) {
                    case 0:
                        tmp[0] = new Vectors(currentVec.velx, currentVec.vely + scale);
                        double[] tmpsolve = { x, y,tmp[0].velx,tmp[0].vely};
                        result = rk2.solve(tmpsolve);
                        tmp[0].distance= distance(result);
                        System.out.println("here");
                        break;
                    case 1:
                        tmp[1] = new Vectors(currentVec.velx, currentVec.vely - scale);
                        double[] tmpsolve1 = { x, y,tmp[1].velx,tmp[1].vely};
                        result = rk2.solve(tmpsolve1);
                        tmp[1].distance= distance(result);
                        System.out.println("here1");
                        break;
                    case 2:
                        tmp[2] = new Vectors(currentVec.velx + scale, currentVec.vely);
                        double[] tmpsolve2 = { x, y,tmp[2].velx,tmp[2].vely};
                        result = rk2.solve(tmpsolve2);
                        tmp[2].distance= distance(result);
                        System.out.println("here2");
                        break;
                    case 3:
                        tmp[3] = new Vectors(currentVec.velx - scale, currentVec.vely);
                        double[] tmpsolve3 = { x, y,tmp[3].velx,tmp[3].vely};
                        result = rk2.solve(tmpsolve3);
                        tmp[3].distance= distance(result);
                        System.out.println("here3");
                        break;
                    case 4:
                        tmp[4] = new Vectors(currentVec.velx - scale, currentVec.vely + scale);
                        double[] tmpsolve4 = { x, y,tmp[4].velx,tmp[4].vely};
                        result = rk2.solve(tmpsolve4);
                        tmp[4].distance= distance(result);
                        System.out.println("here4");
                        break;
                    case 5:
                        tmp[5] = new Vectors(currentVec.velx + scale, currentVec.vely - scale);
                        double[] tmpsolve5 = { x, y,tmp[5].velx,tmp[5].vely};
                        result = rk2.solve(tmpsolve5);
                        tmp[5].distance= distance(result);
                        System.out.println("here5");
                        break;
                    case 6:
                        tmp[6] = new Vectors(currentVec.velx + scale, currentVec.vely + scale);
                        double[] tmpsolve6 = { x, y,tmp[6].velx,tmp[6].vely};
                        result = rk2.solve(tmpsolve6);
                        tmp[6].distance= distance(result);
                        System.out.println("here6");
                        break;
                    case 7:
                        tmp[7] = new Vectors(currentVec.velx + scale, currentVec.vely - scale);
                        double[] tmpsolve7 = { x, y,tmp[7].velx,tmp[7].vely};
                        result = rk2.solve(tmpsolve7);
                        tmp[7].distance= distance(result);
                        System.out.println("here7");
                        break;

                    default:
                        break;
                }

            }
            currentItr++;
            Vectors newBest = currentVec;
            for ( int i=0; i<tmp.length;i++) {
                if(newBest.distance > tmp[i].distance){
                    newBest = tmp[i];
                    System.out.println("im for for swap swap me");
                }
            }
            currentVec=newBest;


        }
        double[] newArrXt = new double[4];
        newArrXt[0]=x;
        newArrXt[1]=y;
        newArrXt[2]=currentVec.velx;
        newArrXt[3]=currentVec.vely;
        System.out.println(currentVec.velx);
        System.out.println(currentVec.vely);
        System.out.println(currentItr);
        return newArrXt;
    }
    public double distance(double[] values) {
        return Math.sqrt(Math.pow(values[0] - read.xt, 2) + Math.pow(values[1] - read.yt, 2));
    }
}
