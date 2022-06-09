package com.badlogic.PhyiscSolvers;

import com.badlogic.FileHandling.FileReader;

public class Acceleration {
    FileReader read = new FileReader();
    double uk = read.muk;
    private final double g = 9.81;

    public double accelerationEquationX(double VelX, double VelY, double derivative) {
        return -g * derivative - uk * g * VelX / Math.sqrt(Math.pow(VelX, 2) + Math.pow(VelY, 2));
    }

    public double accelerationEquationY(double VelX, double VelY, double derivative) {
        return -g * derivative - uk * g * VelY / Math.sqrt(Math.pow(VelX, 2) + Math.pow(VelY, 2));
    }

}

