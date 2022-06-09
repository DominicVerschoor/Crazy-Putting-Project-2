package com.badlogic.PhyiscSolvers;


import com.badlogic.GameLogistics.TerrainInput;

public class PartialDerivative {

    TerrainInput function = new TerrainInput();

    public double partialX(double x, double y){
        return (function.terrain(x+0.0000000001,y)- function.terrain(x,y))/0.0000000001;
    }
    public double partialY(double x, double y){
        return (function.terrain(x,y+0.0000000001)- function.terrain(x,y))/0.0000000001;
    }
}
