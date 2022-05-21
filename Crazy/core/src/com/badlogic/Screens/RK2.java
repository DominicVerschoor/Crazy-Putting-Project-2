package com.badlogic.Screens;

public class RK2 {
    PartialDerivative derive = new PartialDerivative();
    FileReader read = new FileReader();
    private final double g = 9.81;
    double h = 0.0001;
    private double uk = read.muk;     // kinetic friction coefficient of grass
    private double us = read.mus;
    static private double[] newArrXt = new double[4];
    Terrain function = new Terrain();
    Acceleration acc = new Acceleration();

    public double[] BetterEstimationRK2(double[] arrXt) {
        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.01 instead
        double initialX=arrXt[0];
        double initialY=arrXt[1];
        if (arrXt[2] > 5) {
            arrXt[2] = 5;
        }
        if (arrXt[2] < -5) {
            arrXt[2] = -5;
        }
        if (arrXt[3] > 5) {
            arrXt[3] = 5;
        }
        if (arrXt[3] < -5) {
            arrXt[3] = -5;
        }

        while (Math.abs(arrXt[2]) > 0.001 || Math.abs(arrXt[3]) > 0.001) {
            uk = read.muk;
            us = read.mus;
            if ((arrXt[0] >= read.sandPitXMin && arrXt[0] <= read.sandPitXMin)
                    && (arrXt[1] >= read.sandPitXMin && arrXt[1] <= read.sandPitXMin)){
                uk = read.muks;
                us = read.muss;
                System.out.println("ew sand");
            }

            double newerArrXt[] = new double[4];
            double newernewArrXt[] = new double[4];

            System.arraycopy(arrXt, 0, newerArrXt, 0, arrXt.length);
            System.arraycopy(arrXt, 0, newernewArrXt, 0, arrXt.length);

            //Step of Eulers
            newerArrXt[0] = arrXt[0] + h * arrXt[2];     //position + step * velocity --> get the new position X
            newerArrXt[1] = arrXt[1] + h * arrXt[3];     //position + step * velocity --> get the new position Y
            double partialX = derive.partialX(newerArrXt[0], newerArrXt[1]);   //the partial derivative of X
            double partialY = derive.partialY(newerArrXt[0], newerArrXt[1]);    //the partial derivative of Y

            //the second-order derivative equation, which is the acceleration of X, Y
            double accX = -g * partialX - uk * g * arrXt[2] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);
            double accY = -g * partialY - uk * g * arrXt[3] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);

            newerArrXt[2] = arrXt[2] + h * accX;        //X velocity + step * acceleration --> new velocity
            newerArrXt[3] = arrXt[3] + h * accY;        //Y velocity + step * acceleration --> new velocity

            arrXt[0] = newernewArrXt[0] + (newernewArrXt[2] + newerArrXt[2]) / 2 * h;
            arrXt[1] = newernewArrXt[1] + (newernewArrXt[3] + newerArrXt[3]) / 2 * h;
            partialX = derive.partialX(newernewArrXt[0], newernewArrXt[1]);   //the partial derivative of X
            partialY = derive.partialY(newernewArrXt[0], newernewArrXt[1]);    //the partial derivative of Y
            //currentVel += h*(accelarationcurrentpos +acclerationforfutpos)/2
            arrXt[2] += h * (acc.accelerationEquationXx(newernewArrXt, partialX) + acc.accelerationEquationXx(newerArrXt, partialX)) / 2;
            arrXt[3] += h * (acc.accelerationEquationYy(newernewArrXt, partialY) + acc.accelerationEquationYy(newerArrXt, partialY)) / 2;

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

            if ((Math.abs(arrXt[2]) <= 0.001 && Math.abs(arrXt[3]) <= 0.001) && (Math.abs(partialX) > 0.01 || Math.abs(partialY) > 0.01)) {
                double sqrt = Math.sqrt(partialX * partialX + partialY * partialY);
                if (us > sqrt) {
                    break;
                } else {
                    arrXt[2] = h * -g * partialX + uk * g * partialX / sqrt;
                    arrXt[3] = h * -g * partialY + uk * g * partialY / sqrt;

                }
            }
        }

        System.out.println("X: " + arrXt[0]);
        System.out.println("Y: " + arrXt[1]);
        //System.out.println(arrXt[2]);
        //System.out.println(arrXt[3]);
        return arrXt;
    }
}
