package com.badlogic.Screens;

public class partialDerivative {
    Terrain function = new Terrain();
    public double partialX(double x, double y){
         return (function.terrain(x+0.0000000001,y)- function.terrain(x,y))/0.0000000001;
    }
    public double partialY(double x, double y){
        return (function.terrain(x,y+0.0000000001)- function.terrain(x,y))/0.0000000001;
    }
}
