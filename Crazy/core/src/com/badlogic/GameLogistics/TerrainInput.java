package com.badlogic.GameLogistics;
public class TerrainInput{
public double terrain(double x,double y) {
return 0.4*(0.9-Math.exp(-(Math.pow(x,2)+Math.pow(y,2))/8));
}
}
