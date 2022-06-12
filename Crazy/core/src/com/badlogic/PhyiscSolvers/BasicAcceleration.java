package com.badlogic.PhyiscSolvers;

import com.badlogic.FileHandling.FileReader;

public class BasicAcceleration implements Acceleration{
    FileReader read = new FileReader();
    double uk = read.muk;
    private final double g = 9.81;

    @Override
    public double accelerationEquationX(double velX, double velY, double derivativeX, double derivativeY) {
        return -g * derivativeX - uk * g * velX / Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2));
    }

    @Override
    public double accelerationEquationY(double velX, double velY, double derivativeX, double derivativeY) {
        return -g * derivativeY - uk * g * velY / Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2));
    }
}

