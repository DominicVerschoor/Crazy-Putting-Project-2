package com.badlogic.PhyiscSolvers;

public interface Solver {
     double[] solve(double[] ballVector);
     double[] speedLimit(double[] ballVector);
}
