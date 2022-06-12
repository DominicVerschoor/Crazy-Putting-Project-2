package com.badlogic.PhyiscSolvers;

public interface Acceleration {
    public double accelerationEquationX(double velX, double velY, double derivativeX, double derivativeY);

    public double accelerationEquationY(double velX, double velY, double derivativeX, double derivativeY);
}
