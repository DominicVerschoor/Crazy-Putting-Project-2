package com.badlogic.Screens;

public class Euler {
    FileReader read = new FileReader();
    private final double  g=9.81;
    double h = 0.00001;
    private double uk = read.muk;     // kinetic friction coefficient of grass
    private double us = read.mus;
    static private double[] newArrXt = new double[4];
    PartialDerivative Derive = new PartialDerivative();
    Terrain function = new Terrain();


    public double[] Euler(double[] arrXt) {

        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.01 instead
        double initialX=arrXt[0];
        double initialY=arrXt[1];
        if (arrXt[2]>5){
            arrXt[2]=5;
        }
        if (arrXt[2]<-5){
            arrXt[2]=-5;
        }
        if (arrXt[3]>5){
            arrXt[3]=5;
        }
        if (arrXt[3]<-5){
            arrXt[3]=-5;
        }

        while (Math.abs(arrXt[2]) > 0.001 || Math.abs(arrXt[3]) > 0.001) {
            uk = read.muk;
            us = read.mus;
            if ((arrXt[0] >= read.sandPitXMin && arrXt[0] <= read.sandPitXMin)
                    && (arrXt[1] >= read.sandPitXMin && arrXt[1] <= read.sandPitXMin)){
                uk = read.muks;
                us = read.muss;
                //System.out.println("ew sand");
            }

            newArrXt[0] = arrXt[0] + h * arrXt[2];     //position + step * velocity --> get the new position X
            newArrXt[1] = arrXt[1] + h * arrXt[3];     //position + step * velocity --> get the new position Y

            double partialX = Derive.partialX(newArrXt[0], newArrXt[1]);    //the partial derivative of X
            double partialY = Derive.partialY(newArrXt[0], newArrXt[1]);    //the partial derivative of Y

            //the second-order derivative equation, which is the acceleration of X, Y
            double accX = -g * partialX - uk * g * arrXt[2] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);
            double accY = -g * partialY - uk * g * arrXt[3] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);

            newArrXt[2] = arrXt[2] + h * accX;        //X velocity + step * acceleration --> new velocity
            newArrXt[3] = arrXt[3] + h * accY;        //Y velocity + step * acceleration --> new velocity
            if (function.terrain(newArrXt[0], newArrXt[1]) < 0) {
                newArrXt[0] = initialX;
                newArrXt[1] = initialY;
                System.arraycopy(newArrXt, 0, arrXt, 0, arrXt.length);

                //System.out.println("HELP ME im unda tha wata ");
                return newArrXt;
            }
            if (newArrXt[0]>20 || newArrXt[0]<-20 || newArrXt[1]>20 || newArrXt[1]<-20) {
                newArrXt[0] = initialX;
                newArrXt[1] = initialY;
                System.arraycopy(newArrXt, 0, arrXt, 0, arrXt.length);

                //System.out.println("BALL OUT OF BOUNDS");
                return newArrXt;
            }

            System.arraycopy(newArrXt, 0, arrXt, 0, arrXt.length);

            if ((Math.abs(arrXt[2]) <= 0.001 && Math.abs(arrXt[3]) <= 0.001) && (Math.abs(partialX) > 0.001 || Math.abs(partialY) > 0.001)) {
                double sqrt = Math.sqrt(partialX * partialX + partialY * partialY);
                if (us > sqrt) {
                    break;
                } else {
                    arrXt[2] = h * -g * partialX + uk * g * partialX / sqrt;
                    arrXt[3] = h * -g * partialY + uk * g * partialY / sqrt;

                }
            }
        }
        System.out.println(arrXt[2]);
        System.out.println(arrXt[3]);
        return newArrXt;
    }
}
