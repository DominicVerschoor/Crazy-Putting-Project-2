package com.badlogic.Screens;

public class acceleration {
    FileReader read = new FileReader();
    double uk= read.muk;
    private final double  g=9.81;
    public double accelerationEquationXx(double[] ArrXt,double derivative){
        return -g * derivative - uk * g * ArrXt[2] / Math.sqrt(Math.pow(ArrXt[2],2) + Math.pow(ArrXt[3],2));
    }
    public double accelerationEquationYy(double[] ArrXt,double derivative){
        return -g * derivative - uk * g * ArrXt[3] / Math.sqrt(Math.pow(ArrXt[2],2) + Math.pow(ArrXt[3],2));
    }
}
