package com.badlogic.Screens;

public class RK2New {
    PartialDerivative derive = new PartialDerivative();
    FileReader read = new FileReader();
    private final double g = 9.81;
    double h = 0.001;
    private double uk = read.muk;     // kinetic friction coefficient of grass
    private double us = read.mus;
    Terrain function = new Terrain();

    public double accelerationEquationXx(double VelX, double VelY, double derivative) {
        return -g * derivative - uk * g * VelX / Math.sqrt(Math.pow(VelX, 2) + Math.pow(VelY, 2));
    }

    public double accelerationEquationYy(double VelX, double VelY, double derivative) {
        return -g * derivative - uk * g * VelY / Math.sqrt(Math.pow(VelX, 2) + Math.pow(VelY, 2));
    }

    public double[] newRK2(double[] arrXt) {
        double initialX=arrXt[0];
        double initialY=arrXt[1];
//        if (arrXt[2]>5){
//            arrXt[2]=5;
//        }
//        if (arrXt[2]<-5){
//            arrXt[2]=-5;
//        }
//        if (arrXt[3]>5){
//            arrXt[3]=5;
//        }
//        if (arrXt[3]<-5){
//            arrXt[3]=-5;
//        }

        while (Math.abs(arrXt[2]) > 0.001 || Math.abs(arrXt[3]) > 0.001) {
            uk = read.muk;
            us = read.mus;
            if ((arrXt[0] >= read.sandPitXMin && arrXt[0] <= read.sandPitXMin)
                    && (arrXt[1] >= read.sandPitXMin && arrXt[1] <= read.sandPitXMin)){
                uk = read.muks;
                us = read.muss;
                //System.out.println("ew sand");
            }

            double partialx = derive.partialX(arrXt[0], arrXt[1]);    //the partial derivative of X
            double partialy = derive.partialY(arrXt[0], arrXt[1]);    //the partial derivative of Y

            double newerArrXt[] = new double[4];
            System.arraycopy(arrXt, 0, newerArrXt, 0, arrXt.length);

            double k1posX = newerArrXt[0];
            double k1posY = newerArrXt[1];
            double k1VelX = newerArrXt[2];
            double k1VelY = newerArrXt[3];
            partialx = derive.partialX(k1posX, k1posY);
            partialy = derive.partialY(k1posX, k1posY);
            double k1AccelerationX = accelerationEquationXx(k1VelX, k1VelY, partialx);
            double k1AccelerationY = accelerationEquationYy(k1VelX, k1VelY, partialy);

            double k2posX = k1posX + (h * k1VelX) * (2.0 / 3);
            double k2posY = k1posY + (h * k1VelY) * (2.0 / 3);
            double k2VelX = k1VelX + (h * k1AccelerationX) * (2.0 / 3);
            double k2VelY = k1VelY + (h * k1AccelerationY) * (2.0 / 3);
            partialx = derive.partialX(k2posX, k2posY);
            partialy = derive.partialY(k2posX, k2posY);
            double k2AccelerationX = accelerationEquationXx(k2VelX, k2VelY, partialx);
            double k2AccelerationY = accelerationEquationYy(k2VelX, k2VelY, partialy);


            double calculatedXPos = ((k1VelX*h*(1/4.0))+(3/4.0)* (k2VelX*h));
            double calculatedYPos = ((k1VelY*h*(1/4.0))+(3/4.0)* (k2VelY*h));
            double calculatedXVel = ((k1AccelerationX*h*(1/4.0))+(3/4.0)* (k2AccelerationX*h));
            double calculatedYVel = ((k1AccelerationY*h*(1/4.0))+(3/4.0)* (k2AccelerationY*h));

            arrXt[0] += calculatedXPos;
            arrXt[1] += calculatedYPos;
            arrXt[2] += calculatedXVel;
            arrXt[3] += calculatedYVel;

            if (function.terrain(arrXt[0], arrXt[1]) < 0) {
                arrXt[0] = initialX;
                arrXt[1] = initialY;
                System.arraycopy(arrXt, 0, arrXt, 0, arrXt.length);

                System.out.println("HELP ME im unda tha wata ");
                return arrXt;
            }
//            if (arrXt[0]>20 || arrXt[0]<-20 || arrXt[1]>20 || arrXt[1]<-20) {
//                arrXt[0] = initialX;
//                arrXt[1] = initialY;
//                System.arraycopy(arrXt, 0, arrXt, 0, arrXt.length);
//
//                System.out.println("BALL OUT OF BOUNDS");
//                return arrXt;
//            }

            if ((Math.abs(arrXt[2]) <= 0.001 && Math.abs(arrXt[3]) <= 0.001) && (Math.abs(partialx) > 0.001 || Math.abs(partialy) > 0.001)) {
                double sqrt = Math.sqrt(partialx * partialx + partialy * partialy);
                if (us > sqrt) {
                    break;
                } else {
                    arrXt[2] = h * -g * partialx + uk * g * partialx / sqrt;
                    arrXt[3] = h * -g * partialy + uk * g * partialy / sqrt;

                }
            }

        }

//        System.out.println("X: " + arrXt[0]);
//        System.out.println("Y: " + arrXt[1]);
        return arrXt;
    }


}
