package com.badlogic.PhyiscSolvers;

import com.badlogic.FileHandling.FileReader;

public class SteepAcceleration {
    FileReader read = new FileReader();
    double uk = read.muk;
    private final double g = 9.81;
    private final double m = 0.0459;

    public double accelerationEquationX(double velX, double velY, double derivativeX, double derivativeY) {
        double power = 1 + Math.pow(derivativeX, 2) + Math.pow(derivativeY, 2);
        double p1 = -((m*g*derivativeX) / (power));
        double p2 = -((uk*m*g) / (Math.sqrt(power)));
        double p3 = (velX / (Math.sqrt(Math.pow(velX,2) + Math.pow(velY,2) + Math.pow(derivativeX*velX + derivativeY*velY,2))));

        return p1 + p2 * p3;
    }

    public double accelerationEquationYy(double velX, double velY, double derivativeX, double derivativeY) {
        double power = 1 + Math.pow(derivativeX, 2) + Math.pow(derivativeY, 2);
        double p1 = -((m*g*derivativeY) / (power));
        double p2 = -((uk*m*g) / (Math.sqrt(power)));
        double p3 = (velY / (Math.sqrt(Math.pow(velX,2) + Math.pow(velY,2) + Math.pow(derivativeX*velX + derivativeY*velY,2))));

        return p1 + p2 * p3;
    }
}
