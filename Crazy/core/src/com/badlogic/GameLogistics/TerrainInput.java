package com.badlogic.GameLogistics;
public class TerrainInput{
public double terrain(double x,double y) {
return 0.1+(-Math.exp(-(Math.pow(x,2)*3+Math.pow(y,2)*3)));
}
}
