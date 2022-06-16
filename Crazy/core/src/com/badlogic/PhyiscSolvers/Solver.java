package com.badlogic.PhyiscSolvers;

public interface Solver {
     double[] solve(double[] ballVector);
     double[] speedLimit(double[] ballVector);
     void accelerationType(boolean buttonInput);
     boolean isUnderWater(double[] ballVector);
     boolean isOutOfBounds(double[] ballVector);
     boolean isHittingTree(double x1, double x2, double y1, double y2, double radius);
     boolean isRollingDown(double[] ballVector, double partialX, double partialY);
     double[] resetLocation(double[] ballVector, double initialX, double initialY);
     void rollDown(double[] ballVector, double partialx, double partialy);
     void reverseVelocity(double[] ballVector);
}
